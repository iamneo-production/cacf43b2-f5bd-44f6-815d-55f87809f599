package com.teamsix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamsix.dto.ArticleDto;
import com.teamsix.entity.Article;
import com.teamsix.serviceImpl.ArticleServiceImpl;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	
	@Autowired
	private ArticleServiceImpl articleService;
	
	@PostMapping("/user/{userId}")
	public ResponseEntity<String> addArticle(@PathVariable Long userId, @RequestBody Article article){
		return this.articleService.createArticle(userId, article);
	}
	
	@GetMapping
	public ResponseEntity<List<ArticleDto>> getAllArticles(){
		return this.articleService.getAllArticle();
	}
	
	@GetMapping("/{articleId}")
	public ResponseEntity<List<Article>> getArticleById(@PathVariable String articleId){
		return this.articleService.getArticleByArticleId(articleId);
	}
	
	@PostMapping("/{articleId}")
	public ResponseEntity<ArticleDto> createVersion(@PathVariable String articleId, @RequestBody Article article){
		return this.articleService.createVersionOfArticle(articleId, article);
	}
	
	@PutMapping("/{articleId}/version/{versionId}")
	public ResponseEntity<ArticleDto> updateArticle(@PathVariable String articleId, @PathVariable int versionId, @RequestBody Article article){
		return this.articleService.updateArticle(articleId, versionId, article);
	}
	
	@GetMapping("{articleId}/version1/{versionId1}/version2/{versionId2}")
	public ResponseEntity<List<Article>> compareArticles(@PathVariable String articleId, @PathVariable int versionId1, @PathVariable int versionId2){
		return this.articleService.compareArticleByArticleId(articleId, versionId1, versionId2);
	}
	
	@GetMapping("/getString")
	public ResponseEntity<String> getString(){
		return ResponseEntity.ok("This is from article entity");
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ArticleDto>> getAllArticlesByUser(@PathVariable Long userId){
		return this.articleService.getArticlesByUser(userId);
	}
}
	


