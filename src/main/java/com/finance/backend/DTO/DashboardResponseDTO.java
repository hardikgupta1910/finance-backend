package com.finance.backend.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardResponseDTO {
	
	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	private BigDecimal netBalance;
}
