package Finance.Data.Processing.and.Access.Control.DTO;

import lombok.Data;

@Data
public class UserRequestDTO {
	private String email;
	private String password;
	private String userName;
}
