package org.shalim.heybeach.services;

import java.util.List;

import org.shalim.heybeach.domain.requests.RegisterRequest;
import org.shalim.heybeach.model.User;
import org.shalim.heybeach.repositories.UserRepository;
import org.shalim.heybeach.util.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public ReturnCode save(RegisterRequest registerRequest) {
		try {
			User user = new User();
			user.setEmail(registerRequest.getEmail());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			userRepository.save(user);
		} catch (Exception e) {
			LOGGER.debug("Unable to register new user. Exception thrown: {}", e);
			return ReturnCode.USER_ALREADY_EXISTS;
		}
		return ReturnCode.SUCCESS;
	}

	public User getUserByEmail(String email, List<ReturnCode> errors) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			errors.add(ReturnCode.USER_NOT_FOUND);
			LOGGER.error("Fatal error: couldn't find an authenticated user with email {} in the database", email);
		}
		return user;
	}

	public User getCurrentUser(List<ReturnCode> errors) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (email == null) {
			errors.add(ReturnCode.USER_NOT_FOUND);
			return null;
		}
		return getUserByEmail(email, errors);
	}

}
