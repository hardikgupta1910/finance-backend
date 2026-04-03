package Finance.Data.Processing.and.Access.Control.Repository;

import Finance.Data.Processing.and.Access.Control.Model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
	
	List<FinancialRecord> findByUserId(Long userId);
	List<FinancialRecord> findTop5ByUserIdOrderByDateDesc(Long userId);
}
