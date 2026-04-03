package Finance.Data.Processing.and.Access.Control.Model;

import Finance.Data.Processing.and.Access.Control.Domain.Type;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity

public class FinancialRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		@NotNull
	@DecimalMin(value = "0.01")
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	@NotNull
	private Type type;
	@NotBlank
	private String category;
	private LocalDateTime date;
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
