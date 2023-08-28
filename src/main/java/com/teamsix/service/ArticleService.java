package com.teamsix.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.teamsix.dto.ArticleDto;
import com.teamsix.entity.Article;

public interface ArticleService {
	
	public ResponseEntity<String> createArticle(Long userId, Article article);
	public ResponseEntity<List<ArticleDto>> getAllArticle();
	public ResponseEntity<List<ArticleDto>> getArticlesByUser(Long userId);
	public ResponseEntity<List<Article>> getArticleByArticleId(String articleId);
	public ResponseEntity<ArticleDto> createVersionOfArticle(String articleId, Article article);
	public ResponseEntity<ArticleDto> updateArticle(String articleId, int versionId, Article article);
	public ResponseEntity<List<Article>> compareArticleByArticleId(String articleId, int versionId1, int versionId2);
}
