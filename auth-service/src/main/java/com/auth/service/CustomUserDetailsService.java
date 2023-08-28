package com.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.auth.entity.CustomUserDetails;
import com.auth.entity.User;
import com.auth.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> credentials = this.userRepository.findByUsername(username);
		
		return credentials.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException(String.format("Username {} doesn't exist!", username)));
	}

}
