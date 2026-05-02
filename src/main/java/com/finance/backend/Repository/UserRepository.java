package com.finance.backend.Repository;

import com.finance.backend.Domain.Role;
import com.finance.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	boolean existsByRole(Role role);
	long countByRole(Role role);
}
