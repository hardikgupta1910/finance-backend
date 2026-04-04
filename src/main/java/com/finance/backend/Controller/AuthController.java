package com.finance.backend.Controller;

import com.finance.backend.Config.jwtService;
import com.finance.backend.DTO.UserRequestDTO;
import com.finance.backend.Model.User;
import com.finance.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final jwtService jwtService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	public AuthController(jwtService jwtService,
	                      UserService userService,
	                      PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		User user = userService.createUserFromDTO(userRequestDTO);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserRequestDTO request) {
		
		User user = userService.findByEmail(request.getEmail());
		
		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		String token = jwtService.generateToken(user.getId());
		
		return ResponseEntity.ok(token);
	}
}