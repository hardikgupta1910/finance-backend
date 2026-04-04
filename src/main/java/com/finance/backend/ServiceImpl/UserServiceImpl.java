package com.finance.backend.ServiceImpl;

import com.finance.backend.DTO.RoleUpdateDTO;
import com.finance.backend.DTO.StatusUpdateDTO;
import com.finance.backend.DTO.UserRequestDTO;
import com.finance.backend.DTO.UserResponseDTO;
import com.finance.backend.Domain.Role;
import com.finance.backend.Domain.Status;
import com.finance.backend.Model.User;
import com.finance.backend.Repository.UserRepository;
import com.finance.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	

	@Override
	public User createUserFromDTO(UserRequestDTO dto) {
		
		if(dto==null) {
			throw new IllegalArgumentException("User data cannot be null");
		}
		
		if (dto.getEmail() == null || dto.getEmail().isBlank()) {
			throw new IllegalArgumentException("Email cannot be empty");
		}
		if (dto.getPassword() == null || dto.getPassword().isBlank()) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setUserName(dto.getUserName());
		user.setRole(Role.VIEWER); // default
		user.setStatus(Status.ACTIVE);
		
		return userRepository.save(user);
	}
	
	@Override
	public List<User> getAllUsers() {
		
		
		
		return userRepository.findAll();
	}
	
	@Override
	public User getUserById(Long id) {
		
		User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
		
		
		return user;
	}
	
	
	
	@Override
	public User updateUserRole(Long adminId, Long userId, RoleUpdateDTO dto) {
		if (dto == null || dto.getRole() == null) {
			throw new RuntimeException("Role data cannot be null");
		}
		
		
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
		user.setRole(Role.valueOf(dto.getRole()));
		
		return userRepository.save(user);
	}
	
	@Override
	public User updateUserStatus(Long adminId, Long userId, StatusUpdateDTO dto) {
		
		if (dto == null || dto.getStatus() == null) {
			throw new RuntimeException("Status data cannot be null");
		}
		
		
		
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
		user.setStatus(Status.valueOf(dto.getStatus()));
		
		return userRepository.save(user);
	}
	
	@Override
	public void deleteUser( Long requesterID , Long userId) {
		
		
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		long adminCount=userRepository.findAll().stream()
				.filter(r->r.getRole()==Role.ADMIN).count();
		
		if (user.getRole() == Role.ADMIN && adminCount == 1) {
			throw new RuntimeException("Cannot delete the only admin");
		}
		userRepository.delete(user);
	}
	
	@Override
	public User updateUser(Long requesterId, Long userId, User updatedUser) {
		
		if (updatedUser == null) {
			throw new RuntimeException("User data cannot be null");
		}
		
		
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		
		if (updatedUser.getEmail() != null) {
			existingUser.setEmail(updatedUser.getEmail());
		}
		
		if (updatedUser.getPassword() != null) {
			existingUser.setPassword(updatedUser.getPassword());
		}
		
		if (updatedUser.getUserName() != null) {
			existingUser.setUserName(updatedUser.getUserName());
		}
		
		return userRepository.save(existingUser);
	}
	
	public User findByEmail(String email){
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		
		return user;
	}
	
	@Override
	public UserResponseDTO mapToDTO(User user) {
		if (user == null) {
			return null;
		}
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setUserName(user.getUserName());
		dto.setRole(user.getRole().name());
		dto.setStatus(user.getStatus().name());
		return dto;
	}
}

