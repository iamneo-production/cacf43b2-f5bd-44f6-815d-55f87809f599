package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.entity.User;
import com.auth.repository.UserRepository;

@Service
public class AuthService {
	
	 @Autowired
	    private UserRepository repository;
	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private JwtService jwtService;

	    public String addUser(User credential) {
	        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
	        repository.save(credential);
	        return "user added to the system";
	    }

	    public String generateToken(String username) {
	        return jwtService.generateToken(username);
	    }

	    public void validateToken(String token) {
	        jwtService.validateToken(token);
	    }
	    
	    public List<User> getAllUser(){
	    	return repository.findAll();
	    }

}
