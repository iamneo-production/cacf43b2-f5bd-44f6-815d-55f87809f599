package com.teamsix.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.teamsix.entity.Article;

public interface ArticleService {
	
	public ResponseEntity<Article> createArticle(Article article);
	public ResponseEntity<List<Article>> getAllArticle();
	public ResponseEntity<List<Article>> getArticleByArticleId(String articleId);
	public ResponseEntity<Article> createVersionOfArticle(String articleId, Article article);
	public ResponseEntity<Article> updateArticle(String articleId, int versionId, Article article);
	public ResponseEntity<List<Article>> compareArticleByArticleId(String articleId, Integer versionId1, Integer versionId2);
}
