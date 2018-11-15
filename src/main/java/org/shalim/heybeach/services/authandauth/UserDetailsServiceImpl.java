package org.shalim.heybeach.services.authandauth;

import java.util.ArrayList;
import java.util.List;

import org.shalim.heybeach.model.User;
import org.shalim.heybeach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	private static String USER_ROLE = "STANDARD"; // For simplicity, all users have the same role

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(s);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("The user %s doesn't exist", s));
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(USER_ROLE));

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), authorities);
		return userDetails;
	}
}
