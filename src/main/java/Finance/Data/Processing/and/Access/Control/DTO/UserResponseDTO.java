package Finance.Data.Processing.and.Access.Control.DTO;


import lombok.Data;

@Data
public class UserResponseDTO {
	
		private Long id;
		private String email;
		private String userName;
		private String role;
		private String status;
	
}
