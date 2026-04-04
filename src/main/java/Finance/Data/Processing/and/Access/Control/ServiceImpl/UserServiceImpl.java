package Finance.Data.Processing.and.Access.Control.ServiceImpl;

import Finance.Data.Processing.and.Access.Control.DTO.RoleUpdateDTO;
import Finance.Data.Processing.and.Access.Control.DTO.StatusUpdateDTO;
import Finance.Data.Processing.and.Access.Control.DTO.UserRequestDTO;
import Finance.Data.Processing.and.Access.Control.DTO.UserResponseDTO;
import Finance.Data.Processing.and.Access.Control.Domain.Role;
import Finance.Data.Processing.and.Access.Control.Domain.Status;
import Finance.Data.Processing.and.Access.Control.Model.User;
import Finance.Data.Processing.and.Access.Control.Repository.UserRepository;
import Finance.Data.Processing.and.Access.Control.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	@Autowired
	UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	

	@Override
	public User createUserFromDTO(UserRequestDTO dto) {
		
		if(dto==null){
			throw  new IllegalArgumentException("user data cannot be null");
		}
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setUserName(dto.getUserName());
		user.setRole(Role.VIEWER); // default
		user.setStatus(Status.ACTIVE);
		
		return userRepository.save(user);
	}
	
	@Override
	public List<User> getAllUsers(Long adminId) {
		User admin = userRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		if (admin.getRole() != Role.ADMIN) {
			throw new RuntimeException("Access Denied: Only Admin can view all users");
		}
		
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
		User admin=userRepository.findById(adminId).orElseThrow(()->new RuntimeException("User not found"));
		
		if(admin.getRole()!=Role.ADMIN) {
			throw  new RuntimeException("Access Denied: Only Admin can update the users");
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
		
		User admin=userRepository.findById(adminId).orElseThrow(()->new RuntimeException("User not found"));
		if(admin.getRole()!=Role.ADMIN) {
			throw  new RuntimeException("Access Denied: Only Admin can update the users");
		}
		
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
		user.setStatus(Status.valueOf(dto.getStatus()));
		
		return userRepository.save(user);
	}
	
	@Override
	public void deleteUser( Long requesterID , Long userId) {
		
		User requester=userRepository.findById(requesterID).orElseThrow(()->new RuntimeException("User not found"));
		if (!requester.getId().equals(userId) && requester.getRole() != Role.ADMIN) {
			throw new RuntimeException("Access Denied: Cannot update other users");
		}
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
		
		User requester = userRepository.findById(requesterId)
				.orElseThrow(() -> new RuntimeException("Requester not found"));
		
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		
		if (!requester.getId().equals(userId) && requester.getRole() != Role.ADMIN) {
			throw new RuntimeException("Access Denied: Cannot update other users");
		}
		
		
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

