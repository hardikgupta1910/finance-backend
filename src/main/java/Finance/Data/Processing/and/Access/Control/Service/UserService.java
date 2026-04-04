package Finance.Data.Processing.and.Access.Control.Service;

import Finance.Data.Processing.and.Access.Control.DTO.RoleUpdateDTO;
import Finance.Data.Processing.and.Access.Control.DTO.StatusUpdateDTO;
import Finance.Data.Processing.and.Access.Control.DTO.UserRequestDTO;
import Finance.Data.Processing.and.Access.Control.DTO.UserResponseDTO;
import Finance.Data.Processing.and.Access.Control.Model.User;

import java.util.List;

public interface UserService {
//	User createUser(User user);
	
	 User createUserFromDTO(UserRequestDTO dto);
	
	List<User> getAllUsers(Long adminId);
	
	User getUserById(Long id);
	
	UserResponseDTO mapToDTO(User user);
	
	void deleteUser( Long adminIdId, Long userId);
	
	User updateUserRole(Long adminId, Long userId, RoleUpdateDTO dto);
	
	User updateUserStatus(Long adminId, Long userId, StatusUpdateDTO dto);
	
	User updateUser(Long requesterId, Long userId, User updatedUser);
	
	User findByEmail(String email);
}
