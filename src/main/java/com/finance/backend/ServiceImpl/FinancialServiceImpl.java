package com.finance.backend.ServiceImpl;

import com.finance.backend.DTO.FinancialRecordDTO;
import com.finance.backend.DTO.FinancialRecordRequestDTO;
import com.finance.backend.Domain.Type;
import com.finance.backend.Model.FinancialRecord;
import com.finance.backend.Model.User;
import com.finance.backend.Repository.FinancialRecordRepository;
import com.finance.backend.Repository.UserRepository;
import com.finance.backend.Service.FinancialRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FinancialServiceImpl implements FinancialRecordService {
	
	private final FinancialRecordRepository financialRecordRepository;
	private final UserRepository userRepository;
	@Autowired
	 FinancialServiceImpl (FinancialRecordRepository financialRecordRepository, UserRepository userRepository) {
		this.financialRecordRepository=financialRecordRepository;
		this.userRepository=userRepository;
	}
	


	@Override
	public FinancialRecord createRecord(Long userId, FinancialRecord record) {

		if (record == null) {
			throw new RuntimeException("Record cannot be null");
		}

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		//  Business validations
		if (record.getAmount() == null || record.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Amount must be greater than 0");
		}

		if (record.getType() == null) {
			throw new RuntimeException("Type is required");
		}

		if (record.getDate() == null) {
			throw new RuntimeException("Date is required");
		}

		//  Assign ownership
		record.setUser(user);

		return financialRecordRepository.save(record);
	}

	




	@Override
	@Transactional
	public FinancialRecord updateRecord(Long recordId, Long userId, FinancialRecord updatedData) {

		if (updatedData == null) {
			throw new RuntimeException("Record data cannot be null");
		}

		//  Fetch record WITH ownership check
		FinancialRecord record = financialRecordRepository
				.findByIdAndUserId(recordId, userId)
				.orElseThrow(() -> new RuntimeException("Record not found or unauthorized"));

		//  Apply updates with validation

		if (updatedData.getAmount() != null) {
			if (updatedData.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
				throw new RuntimeException("Amount must be greater than 0");
			}
			record.setAmount(updatedData.getAmount());
		}

		if (updatedData.getType() != null) {
			record.setType(updatedData.getType());
		}

		if (updatedData.getCategory() != null) {
			record.setCategory(updatedData.getCategory());
		}

		if (updatedData.getDate() != null) {
			record.setDate(updatedData.getDate());
		}

		if (updatedData.getNote() != null) {
			record.setNote(updatedData.getNote());
		}

		return financialRecordRepository.save(record);
	}


	
	@Override
	public Page<FinancialRecord> getRecords(Long userId, String type, String category, Pageable pageable) {
		
		// Case 1: both filters present
		if (type != null && category != null) {
			return financialRecordRepository.findByUserIdAndTypeAndCategory(userId,
							Type.valueOf(type.toUpperCase()),
							category,
							pageable
					);
		}
		
		// Case 2: only type
		if (type != null) {
			return financialRecordRepository
					.findByUserIdAndType(
							userId,
							Type.valueOf(type.toUpperCase()),
							pageable
					);
		}
		
		// Case 3: only category
		if (category != null) {
			return financialRecordRepository
					.findByUserIdAndCategory(
							userId,
							category,
							pageable
					);
		}
		
		// Case 4: no filters
		return financialRecordRepository.findByUserId(userId, pageable);
	}
	


	@Override
	@Transactional
	public void deleteRecord(Long recordId, Long userId) {

		FinancialRecord record = financialRecordRepository
				.findByIdAndUserId(recordId, userId)
				.orElseThrow(() -> new RuntimeException("Record not found or unauthorized"));

		financialRecordRepository.delete(record);
	}
	


	@Override
	public BigDecimal getTotalIncome(Long userId) {
		return Optional.ofNullable(
				financialRecordRepository.sumByUserIdAndType(userId, Type.INCOME)
		).orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal netBalance(Long userId){
		BigDecimal income=getTotalIncome(userId);
		BigDecimal expense=getTotalExpense(userId);
		
		return  income.subtract(expense);
	}
	


	@Override
	public BigDecimal getTotalExpense(Long userId) {
		return Optional.ofNullable(
				financialRecordRepository.sumByUserIdAndType(userId, Type.EXPENSE)
		).orElse(BigDecimal.ZERO);
	}
	
	@Override
	public FinancialRecordDTO mapToDTO(FinancialRecord record) {
		FinancialRecordDTO dto = new FinancialRecordDTO();
		dto.setId(record.getId());
		dto.setAmount(record.getAmount());
		dto.setCategory(record.getCategory());
		dto.setDate(record.getDate());
		dto.setNote(record.getNote());
		dto.setType(record.getType().name());
		
		return dto;
	}
	
	
	public FinancialRecord convertToEntity(FinancialRecordRequestDTO dto) {
		
		FinancialRecord record = new FinancialRecord();
		
		record.setAmount(dto.getAmount());
		try {
			record.setType(Type.valueOf(dto.getType())); // converting String to Enum
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid type. Must be INCOME or EXPENSE");
		}
		record.setCategory(dto.getCategory());
		record.setDate(dto.getDate());
		record.setNote(dto.getNote());
		
		return record;
	}
	
	public Map<String, BigDecimal> getCategoryTotals(Long UserId){
	
		
		List<FinancialRecord> records=financialRecordRepository.findByUserId(UserId);
		Map<String, BigDecimal> result = new HashMap<>();
		
		for (FinancialRecord record : records) {
			
			if (record.getType() != Type.EXPENSE) continue;
			
			String category = record.getCategory();
			BigDecimal amount = record.getAmount();
			
			result.put(
					category,
					result.getOrDefault(category, BigDecimal.ZERO).add(amount)
			);
		}
		
		return result;
	}
	
	@Override
	public Page<FinancialRecord> searchRecords(
			Long userId,
			String keyword,
			Pageable pageable) {
		

		return financialRecordRepository
				.findByUserIdAndCategoryContainingIgnoreCaseOrUserIdAndNoteContainingIgnoreCase(
						userId,
						keyword,
						userId,
						keyword,
						pageable
				);
	}
	
	@Override
	public List<FinancialRecordDTO> getRecentActivity(Long userId) {
		
		List<FinancialRecord> records =
				financialRecordRepository.findTop5ByUserIdOrderByDateDesc(userId);
		
		return records.stream()
				.map(this::mapToDTO)
				.toList();
	}






}
