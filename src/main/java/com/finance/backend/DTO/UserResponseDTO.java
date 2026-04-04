package com.finance.backend.DTO;


import lombok.Data;

@Data
public class UserResponseDTO {
	
		private Long id;
		private String email;
		private String userName;
		private String role;
		private String status;
	
}
