package com.teamsix.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.teamsix.dto.ArticleContent;
import com.teamsix.dto.ArticleDto;
import com.teamsix.entity.User;
import com.teamsix.openFeign.ArticleFeign;
import com.teamsix.repository.UserRepository;
import com.teamsix.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private ArticleFeign articleFeign;
	
	
	@Override
	public ResponseEntity<String> registerUser(User user) {
		try {
			this.userRepository.save(user);
			return ResponseEntity.ok("User registered successfully!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<User> getUserByUserId(Long userId) {
		try {
			User user = this.userRepository.findByUserId(userId);
			if(Objects.nonNull(user)) return ResponseEntity.ok(user);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getUserByRole(String role) {
		try {
			List<User> userRoleList = this.userRepository.findByRole(role);
			if(!userRoleList.isEmpty()) return ResponseEntity.ok(userRoleList);
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@Override
	public ResponseEntity<String> getString() {
		return this.articleFeign.getString();
	}

	@Override
	public ResponseEntity<String> createArticle(Long userId, ArticleContent articleContent) {
		return this.articleFeign.addArticle(userId, articleContent);
	}

	@Override
	public ResponseEntity<List<ArticleDto>> getAllArticlesByUser(Long userId) {
		try {
			if(Objects.nonNull(getUserByUserId(userId))) {
				return this.articleFeign.getAllArticlesByUser(userId);
			}
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
