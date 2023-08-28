package com.auth.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.auth.entity.User;

public interface UserService{
	public ResponseEntity<List<User>> getAllUsers();
	public ResponseEntity<List<User>> getAllUserByRole(String role);
}
