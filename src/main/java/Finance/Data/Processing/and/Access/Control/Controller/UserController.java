package Finance.Data.Processing.and.Access.Control.Controller;

import Finance.Data.Processing.and.Access.Control.DTO.RoleUpdateDTO;
import Finance.Data.Processing.and.Access.Control.DTO.StatusUpdateDTO;
import Finance.Data.Processing.and.Access.Control.DTO.UserRequestDTO;
import Finance.Data.Processing.and.Access.Control.DTO.UserResponseDTO;
import Finance.Data.Processing.and.Access.Control.Model.User;
import Finance.Data.Processing.and.Access.Control.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
		User user=userService.getUserById(id);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestParam Long adminId) {
		List<User> users=userService.getAllUsers(adminId);
		List<UserResponseDTO>response = users.stream().map(userService::mapToDTO).toList();

		return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> createUserFromDTO(@Valid  @RequestBody UserRequestDTO  userRequestDTO) {
		User user=userService.createUserFromDTO(userRequestDTO);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	@PatchMapping("/{userId}/role")
	public ResponseEntity<UserResponseDTO> updateUserRole(@RequestParam Long adminId, @PathVariable Long userId, @RequestBody RoleUpdateDTO dto) {
		User user=userService.updateUserRole(adminId, userId, dto);
		return ResponseEntity.ok(userService.mapToDTO(user));
	}
	
	@PatchMapping("/{userId}/status")
	public  ResponseEntity<UserResponseDTO> updateUserStatus(@RequestParam Long adminId, @PathVariable Long userId,  @RequestBody StatusUpdateDTO dto){
		User user=userService.updateUserStatus(adminId, userId, dto);
		return ResponseEntity.ok(userService.mapToDTO(user));	}
	
	@PatchMapping("/{userId}")
	public  ResponseEntity<User> updateUser(@RequestParam Long requesterId, @PathVariable Long userId, @RequestBody User user) {
		
		return ResponseEntity.ok(userService.updateUser(requesterId, userId, user));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@RequestParam Long requesterId, @PathVariable Long userId) {
		
		userService.deleteUser(requesterId, userId);
		return ResponseEntity.ok("User deleted successfully");
	}
	
}
