package Finance.Data.Processing.and.Access.Control.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
 class ErrorResponse {
	
	private String message;
	private int status;
	private LocalDateTime timestamp;
}