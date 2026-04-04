package Finance.Data.Processing.and.Access.Control.Controller;

import Finance.Data.Processing.and.Access.Control.Config.jwtService;
import Finance.Data.Processing.and.Access.Control.DTO.UserRequestDTO;
import Finance.Data.Processing.and.Access.Control.Model.User;
import Finance.Data.Processing.and.Access.Control.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private jwtService jwtService;
	private final UserService userService;
	AuthController(jwtService jwtService, UserService userService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?>signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		User user=userService.createUserFromDTO(userRequestDTO);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserRequestDTO request) {
		
		User user = userService.findByEmail(request.getEmail());
		
		if (!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		String token = jwtService.generateToken(user.getId());
		
		return ResponseEntity.ok(token);
	}

}
