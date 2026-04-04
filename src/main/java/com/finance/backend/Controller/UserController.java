package com.finance.backend.Controller;

import com.finance.backend.DTO.RoleUpdateDTO;
import com.finance.backend.DTO.StatusUpdateDTO;
import com.finance.backend.DTO.UserRequestDTO;
import com.finance.backend.DTO.UserResponseDTO;
import com.finance.backend.Model.User;
import com.finance.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	@Autowired
	UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	private Long getUserId() {
		return (Long) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
	}

	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
	public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		
		
		List<User> users = userService.getAllUsers();
		List<UserResponseDTO> response =
				users.stream().map(userService::mapToDTO).toList();
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDTO> createUserFromDTO(
			@Valid @RequestBody UserRequestDTO userRequestDTO) {
		
		User user = userService.createUserFromDTO(userRequestDTO);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	@PatchMapping("/{userId}/role")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDTO> updateUserRole(
			@PathVariable Long userId,
			@RequestBody RoleUpdateDTO dto) {
		
		Long adminId = getUserId();
		
		User user = userService.updateUserRole(adminId, userId, dto);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	@PatchMapping("/{userId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDTO> updateUserStatus(
			@PathVariable Long userId,
			@RequestBody StatusUpdateDTO dto) {
		
		Long adminId = getUserId();
		
		User user = userService.updateUserStatus(adminId, userId, dto);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	@PatchMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal")
	public ResponseEntity<User> updateUser(
			@PathVariable Long userId,
			@RequestBody User user) {
		
		Long requesterId = getUserId();
		
		return ResponseEntity.ok(userService.updateUser(requesterId, userId, user));
	}
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		
		Long requesterId = getUserId();
		
		userService.deleteUser(requesterId, userId);
		return ResponseEntity.ok("User deleted successfully");
	}
}
	

