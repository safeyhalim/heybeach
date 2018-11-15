package org.shalim.heybeach.repositories;

import org.shalim.heybeach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
