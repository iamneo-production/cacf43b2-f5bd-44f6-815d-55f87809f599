package com.teamsix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamsix.dto.ArticleContent;
import com.teamsix.dto.ArticleDto;
import com.teamsix.entity.User;
import com.teamsix.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user){
		return this.userService.registerUser(user);
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable Long userId){
		return this.userService.getUserByUserId(userId);
	}
	
	@GetMapping("/role/{role}")
	public ResponseEntity<List<User>> getAllUserByRole(@PathVariable String role){
		return this.userService.getUserByRole(role);
	}
	
	@PostMapping("/{userId}/create")
	public ResponseEntity<String> createArticle(@PathVariable Long userId, @RequestBody ArticleContent articleContent){
		return this.userService.createArticle(userId, articleContent);
	}
	
	@GetMapping("/getString")
	public String getString() {
		return "This is from user microservice";
	}
	
	@GetMapping("/{userId}/article")
	public ResponseEntity<List<ArticleDto>> getArticleByUser(@PathVariable Long userId){
		return this.userService.getAllArticlesByUser(userId);
	}

}
