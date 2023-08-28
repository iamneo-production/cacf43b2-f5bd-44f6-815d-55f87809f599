package com.teamsix.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.teamsix.dto.ArticleContent;
import com.teamsix.dto.ArticleDto;
import com.teamsix.entity.User;

public interface UserService {
	
	public ResponseEntity<String> registerUser(User user);
	public ResponseEntity<User> getUserByUserId(Long userId);
	public ResponseEntity<List<User>> getUserByRole(String role);
	public ResponseEntity<String> getString();
	public ResponseEntity<String> createArticle(Long userId, ArticleContent articleContent);
	public ResponseEntity<List<ArticleDto>> getAllArticlesByUser(Long userId);

}
