package com.auth.serviceImpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth.entity.User;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			return ResponseEntity.ok(this.userRepository.findAll());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@Override
	public ResponseEntity<List<User>> getAllUserByRole(String role) {
		try {
			return ResponseEntity.ok(this.userRepository.findByRole(role));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
