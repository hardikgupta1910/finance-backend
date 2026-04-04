package com.finance.backend.Controller;

import com.finance.backend.DTO.DashboardResponseDTO;
import com.finance.backend.DTO.FinancialRecordDTO;
import com.finance.backend.DTO.FinancialRecordRequestDTO;
import com.finance.backend.Model.FinancialRecord;
import com.finance.backend.Service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;



@RestController
@RequestMapping("/records")
public class FinancialRecordController {
	
	private final FinancialRecordService financialRecordService;
	
	@Autowired
	FinancialRecordController(FinancialRecordService financialRecordService) {
		this.financialRecordService = financialRecordService;
	}
	
	private Long getUserId() {
		return (Long) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	public List<FinancialRecordDTO> getRecords(
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String category) {
		
		Long userId = getUserId();
		
		List<FinancialRecord> records =
				financialRecordService.getRecords(userId, type, category);
		
		return records.stream().map(financialRecordService::mapToDTO).toList();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FinancialRecordDTO> createRecord(
			@Valid @RequestBody FinancialRecordRequestDTO dto) {
		
		Long userId = getUserId();
		
		FinancialRecord record = financialRecordService.convertToEntity(dto);
		FinancialRecord saved = financialRecordService.createRecord(userId, record);
		
		return ResponseEntity.ok(financialRecordService.mapToDTO(saved));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FinancialRecordDTO> updateRecord(
			@PathVariable Long id,
			@Valid @RequestBody FinancialRecordRequestDTO dto) {
		
		Long userId = getUserId();
		
		FinancialRecord record = financialRecordService.convertToEntity(dto);
		FinancialRecord updated =
				financialRecordService.updateRecord(id, userId, record);
		
		return ResponseEntity.ok(financialRecordService.mapToDTO(updated));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
		
		Long userId = getUserId();
		
		financialRecordService.deleteRecord(id, userId);
		return ResponseEntity.ok("Record Deleted Successfully");
	}
	
	@GetMapping("/summary")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DashboardResponseDTO> getSummary() {
		
		Long userId = getUserId();
		
		DashboardResponseDTO dto = new DashboardResponseDTO();
		dto.setTotalIncome(financialRecordService.getTotalIncome(userId));
		dto.setTotalExpense(financialRecordService.getTotalExpense(userId));
		dto.setNetBalance(financialRecordService.netBalance(userId));
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/summary/category")
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	public ResponseEntity<Map<String, BigDecimal>> getCategorySummary() {
		
		Long userId = getUserId();
		return ResponseEntity.ok(financialRecordService.getCategoryTotals(userId));
	}
	
	@GetMapping("/recent")
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	public ResponseEntity<List<FinancialRecordDTO>> getRecentActivity() {
		
		Long userId = getUserId();
		return ResponseEntity.ok(financialRecordService.getRecentActivity(userId));
	}
}
