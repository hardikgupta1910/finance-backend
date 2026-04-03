package Finance.Data.Processing.and.Access.Control.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinancialRecordDTO {
	private Long id;
	private BigDecimal amount;
	private String type;
	private String category;
	private LocalDateTime date;
	private String note;
}
