package com.finance.backend.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinancialRecordRequestDTO {
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private String type;
	
	@NotNull
	private String category;
	
	private LocalDateTime date;
	
	private String note;
}
