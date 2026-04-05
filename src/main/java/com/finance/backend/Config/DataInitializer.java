package com.finance.backend.Config;

import com.finance.backend.Domain.Role;
import com.finance.backend.Domain.Status;
import com.finance.backend.Model.User;
import com.finance.backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public DataInitializer(UserRepository userRepository,
	                       PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@Value("${ADMIN_EMAIL:admin@finance.com}")
	private String adminEmail;
	
	@Value("${ADMIN_PASSWORD:admin123}")
	private String adminPassword;
	
	private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
	@Override
	public void run(String... args) {
		
		boolean adminExists = userRepository.existsByRole(Role.ADMIN);
		
		if (!adminExists) {
			User admin = new User();
			admin.setEmail(adminEmail);
			admin.setUserName("admin");
			admin.setPassword(passwordEncoder.encode(adminPassword));
			admin.setRole(Role.ADMIN);
			admin.setStatus(Status.ACTIVE);
			
			userRepository.save(admin);
			
			log.info("Admin created successfully");
		}
	}
}
