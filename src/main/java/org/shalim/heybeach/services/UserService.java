package org.shalim.heybeach.services;

import org.shalim.heybeach.domain.requests.RegisterRequest;
import org.shalim.heybeach.model.User;
import org.shalim.heybeach.repositories.UserRepository;
import org.shalim.heybeach.util.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
