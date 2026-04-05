package com.finance.backend.Config;

import com.finance.backend.Domain.Role;
import com.finance.backend.Domain.Status;
import com.finance.backend.Model.User;
import com.finance.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	@Autowired
	 DataInitializer(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		boolean adminExists = userRepository.existsByRole(Role.ADMIN);
		
		if (!adminExists) {
			User admin = new User();
			admin.setEmail("admin@finance.com");
			admin.setUserName("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			admin.setStatus(Status.ACTIVE);
			
			userRepository.save(admin);
			
			System.out.println("Default admin created");
		}
		
	}
}
