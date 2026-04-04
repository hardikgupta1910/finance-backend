package com.finance.backend.Controller;

import com.finance.backend.DTO.DashboardResponseDTO;
import com.finance.backend.DTO.FinancialRecordDTO;
import com.finance.backend.DTO.FinancialRecordRequestDTO;
import com.finance.backend.Model.FinancialRecord;
import com.finance.backend.Service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

//@RestController
//@RequestMapping("/records")
//public class FinancialRecordController {
//	private final FinancialRecordService financialRecordService;
//
//	@Autowired
//	FinancialRecordController(FinancialRecordService financialRecordService) {
//		this.financialRecordService = financialRecordService;
//	}
//
//
//	@GetMapping
//	public List<FinancialRecordDTO> getRecords(@RequestParam Long userId,
//											   @RequestParam(required = false) String type,
//											   @RequestParam(required = false) String category) {
//		List<FinancialRecord> records=financialRecordService.getRecords(userId,type,category);
//		 return records.stream().map(financialRecordService::mapToDTO).toList();
//	}
//
//	@PostMapping
//	public ResponseEntity<FinancialRecordDTO> createRecord(@RequestParam Long userId, @Valid @RequestBody FinancialRecordRequestDTO dto) {
//		FinancialRecord record=financialRecordService.convertToEntity(dto);
//		FinancialRecord saved = financialRecordService.createRecord(userId, record);
//		return ResponseEntity.ok(financialRecordService.mapToDTO(saved));
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<FinancialRecordDTO> updateRecord(@PathVariable Long id,  @RequestParam Long userId , @Valid @RequestBody FinancialRecordRequestDTO dto) {
//		FinancialRecord record=financialRecordService.convertToEntity(dto);
//		FinancialRecord updated = financialRecordService.updateRecord(id,userId, record);
//		return ResponseEntity.ok(financialRecordService.mapToDTO(updated));
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteRecord(@PathVariable Long id, @RequestParam Long userId) {
//
//		financialRecordService.deleteRecord(id, userId);
//		return ResponseEntity.ok("Record Deleted Successfully");
//	}
//
//	@GetMapping("/summary")
//	public ResponseEntity<?> getSummary(@RequestParam Long userId) {
//		DashboardResponseDTO dto = new DashboardResponseDTO();
//
//		dto.setTotalIncome(financialRecordService.getTotalIncome(userId));
//		dto.setTotalExpense(financialRecordService.getTotalExpense(userId));
//		dto.setNetBalance(financialRecordService.netBalance(userId));
//
//		return ResponseEntity.ok(dto);
//	}
//
//	@GetMapping("/summary/category")
//		public ResponseEntity<Map<String, BigDecimal>> getCategorySummary(@RequestParam Long userId) {
//			return ResponseEntity.ok(financialRecordService.getCategoryTotals(userId));
//		}
//	@GetMapping("/recent")
//	public ResponseEntity<List<FinancialRecordDTO>> getRecentActivity(@RequestParam Long userId) {
//		return ResponseEntity.ok(financialRecordService.getRecentActivity(userId));
//	}
	
//}

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
	public List<FinancialRecordDTO> getRecords(
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String category) {
		
		Long userId = getUserId();
		
		List<FinancialRecord> records =
				financialRecordService.getRecords(userId, type, category);
		
		return records.stream().map(financialRecordService::mapToDTO).toList();
	}
	
	@PostMapping
	public ResponseEntity<FinancialRecordDTO> createRecord(
			@Valid @RequestBody FinancialRecordRequestDTO dto) {
		
		Long userId = getUserId();
		
		FinancialRecord record = financialRecordService.convertToEntity(dto);
		FinancialRecord saved = financialRecordService.createRecord(userId, record);
		
		return ResponseEntity.ok(financialRecordService.mapToDTO(saved));
	}
	
	@PutMapping("/{id}")
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
	public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
		
		Long userId = getUserId();
		
		financialRecordService.deleteRecord(id, userId);
		return ResponseEntity.ok("Record Deleted Successfully");
	}
	
	@GetMapping("/summary")
	public ResponseEntity<DashboardResponseDTO> getSummary() {
		
		Long userId = getUserId();
		
		DashboardResponseDTO dto = new DashboardResponseDTO();
		dto.setTotalIncome(financialRecordService.getTotalIncome(userId));
		dto.setTotalExpense(financialRecordService.getTotalExpense(userId));
		dto.setNetBalance(financialRecordService.netBalance(userId));
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/summary/category")
	public ResponseEntity<Map<String, BigDecimal>> getCategorySummary() {
		
		Long userId = getUserId();
		return ResponseEntity.ok(financialRecordService.getCategoryTotals(userId));
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<FinancialRecordDTO>> getRecentActivity() {
		
		Long userId = getUserId();
		return ResponseEntity.ok(financialRecordService.getRecentActivity(userId));
	}
}
