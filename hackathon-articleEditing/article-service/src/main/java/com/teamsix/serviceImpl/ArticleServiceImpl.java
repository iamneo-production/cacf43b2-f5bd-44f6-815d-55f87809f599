package com.teamsix.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.teamsix.entity.Article;
import com.teamsix.repository.ArticleRepository;
import com.teamsix.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public ResponseEntity<Article> createArticle(Article article) {
		try {
			return ResponseEntity.ok(this.articleRepository.save(article));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Article>> getAllArticle() {
		try {
			return ResponseEntity.ok(this.articleRepository.findAll());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Article>> getArticleByArticleId(String articleId) {
		try {
			List<Article> article = this.articleRepository.findByArticleId(articleId);
			if(Objects.nonNull(article)) return ResponseEntity.ok(article);
			return new ResponseEntity<>(article, HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public int getCountOfArticlesByArticleId(String articleId) {
		List<Article> articleList = this.articleRepository.findAll();
		return articleList.stream().filter(resp-> resp.getArticleId().equals(articleId)).collect(Collectors.toList()).size();
	}

	@Override
	public ResponseEntity<Article> createVersionOfArticle(String articleId, Article article) {
		try {
			List<Article> articleObj = this.articleRepository.findByArticleId(articleId);
			
			if(Objects.nonNull(articleObj)) {
				int versionCount = getCountOfArticlesByArticleId(articleId);
				article.setCreationDate(articleObj.get(0).getCreationDate());
				System.out.println(articleObj.get(0).getCreationDate());
				article.setArticleId(articleId);
				article.setCurrentVersionId(versionCount+1);
				return ResponseEntity.ok(this.articleRepository.save(article));
			}
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Article> updateArticle(String articleId, int versionId, Article article) {
		try {
			
			Article articleObj = this.articleRepository.getArticleByArticleIdAndVersionId(articleId, versionId);
			if(Objects.nonNull(articleObj)) {
				articleObj.setTitle(article.getTitle());
				articleObj.setContent(article.getContent());
				return ResponseEntity.ok(this.articleRepository.save(articleObj));
			}
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Article>> compareArticleByArticleId(String articleId, Integer versionId1, Integer versionId2) {
		try {
			
			List<Article> articleList = this.articleRepository.findByArticleId(articleId);
			List<Article> compareArticleList = new ArrayList<>();
			
			if(Objects.nonNull(articleList)) {
				
				for(Article article: articleList) {
						
					if(article.getCurrentVersionId() == versionId1) compareArticleList.add(article);
					if(article.getCurrentVersionId() == versionId2) compareArticleList.add(article);
				}
				

				
				return ResponseEntity.ok(compareArticleList);
			
			}
			
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
