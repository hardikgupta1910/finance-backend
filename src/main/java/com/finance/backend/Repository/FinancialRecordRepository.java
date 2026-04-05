package com.finance.backend.Repository;

import com.finance.backend.Domain.Type;
import com.finance.backend.Model.FinancialRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
	
	List<FinancialRecord> findByUserId(Long userId);
	List<FinancialRecord> findTop5ByUserIdOrderByDateDesc(Long userId);
	Page<FinancialRecord> findByUserId(Long userId, Pageable pageable);
	
	Page<FinancialRecord> findByUserIdAndType(Long userId, Type type, Pageable pageable);
	
	Page<FinancialRecord> findByUserIdAndCategory(Long userId, String category, Pageable pageable);
	
	Page<FinancialRecord> findByUserIdAndTypeAndCategory(Long userId, Type type, String category, Pageable pageable);
	
	Page<FinancialRecord> findByUserIdAndCategoryContainingIgnoreCaseAndNoteContainingIgnoreCase(
			Long userId,
			String category,
			String note,
			Pageable pageable
	);
}
