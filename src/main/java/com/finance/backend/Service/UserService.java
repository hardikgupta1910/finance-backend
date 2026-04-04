package com.finance.backend.Service;

import com.finance.backend.DTO.RoleUpdateDTO;
import com.finance.backend.DTO.StatusUpdateDTO;
import com.finance.backend.DTO.UserRequestDTO;
import com.finance.backend.DTO.UserResponseDTO;
import com.finance.backend.Model.User;

import java.util.List;

public interface UserService {
//	User createUser(User user);
	
	 User createUserFromDTO(UserRequestDTO dto);
	
	List<User> getAllUsers();
	
	User getUserById(Long id);
	
	UserResponseDTO mapToDTO(User user);
	
	void deleteUser( Long adminIdId, Long userId);
	
	User updateUserRole(Long adminId, Long userId, RoleUpdateDTO dto);
	
	User updateUserStatus(Long adminId, Long userId, StatusUpdateDTO dto);
	
	User updateUser(Long requesterId, Long userId, User updatedUser);
	
	User findByEmail(String email);
}
