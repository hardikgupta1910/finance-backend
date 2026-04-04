package com.finance.backend.Service;

import com.finance.backend.DTO.FinancialRecordDTO;
import com.finance.backend.DTO.FinancialRecordRequestDTO;
import com.finance.backend.Model.FinancialRecord;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface FinancialRecordService {
	FinancialRecord createRecord(Long userId, FinancialRecord record);
	
	FinancialRecord updateRecord(Long recordId,Long userId, FinancialRecord record);
	

List<FinancialRecord> getRecords(Long userId, String type, String category);
	void deleteRecord(Long recordId, Long userId);
	
	FinancialRecordDTO mapToDTO(FinancialRecord record);
	
	FinancialRecord convertToEntity(FinancialRecordRequestDTO dto);
	
	BigDecimal getTotalIncome(Long userId);
	BigDecimal getTotalExpense(Long userId);
	BigDecimal netBalance(Long userId);
	Map<String, BigDecimal> getCategoryTotals(Long UserId);
	
	List<FinancialRecordDTO> getRecentActivity(Long userId);
}
