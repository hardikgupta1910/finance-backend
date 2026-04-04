package com.finance.backend.Repository;

import com.finance.backend.Model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
	
	List<FinancialRecord> findByUserId(Long userId);
	List<FinancialRecord> findTop5ByUserIdOrderByDateDesc(Long userId);
}
