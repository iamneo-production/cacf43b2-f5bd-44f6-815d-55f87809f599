package com.teamsix.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.teamsix.dto.ArticleDto;
import com.teamsix.dto.User;
import com.teamsix.dto.UserDto;
import com.teamsix.entity.Article;
import com.teamsix.openFeign.UserFeign;
import com.teamsix.repository.ArticleRepository;
import com.teamsix.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserFeign userFeign;
	
	@Override
	public ResponseEntity<String> createArticle(Long userId, Article article) {
		try {
			article.setUserId(userId);
			this.articleRepository.save(article);
			return ResponseEntity.ok("Article created!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ArticleDto>> getAllArticle() {
		try {
			 List<Article> articleList = this.articleRepository.findAll();
			 return ResponseEntity.ok(
					 articleList.stream().map(this::fromEntityToDto).collect(Collectors.toList())
					 );
			 
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
	public ResponseEntity<ArticleDto> createVersionOfArticle(String articleId, Article article) {
		try {
			List<Article> articleObj = this.articleRepository.findByArticleId(articleId);
			
			if(Objects.nonNull(articleObj)) {
				int versionCount = getCountOfArticlesByArticleId(articleId);
				article.setCreationDate(articleObj.get(0).getCreationDate());
				System.out.println(articleObj.get(0).getCreationDate());
				article.setArticleId(articleId);
				article.setUserId(articleObj.get(0).getUserId());
				article.setCurrentVersionId(versionCount+1);
				this.articleRepository.save(article);
				ArticleDto articleDto = fromEntityToDto(article);
				return ResponseEntity.ok(articleDto);
			}
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ArticleDto> updateArticle(String articleId, int versionId, Article article) {
		try {
			
			Article articleObj = this.articleRepository.getArticleByArticleIdAndVersionId(articleId, versionId);
			if(Objects.nonNull(articleObj)) {
				articleObj.setTitle(article.getTitle());
				articleObj.setContent(article.getContent());
				this.articleRepository.save(articleObj);
				ArticleDto articleDto = fromEntityToDto(articleObj);
				return ResponseEntity.ok(articleDto);
			}
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Article>> compareArticleByArticleId(String articleId, int versionId1, int versionId2) {
		try {
			
			List<Article> articleList = this.articleRepository.findByArticleId(articleId);
			List<Article> compareArticleList = new ArrayList<>();
			
			if(Objects.nonNull(articleList)) {
				
				for(Article article: articleList) {
						
					if(article.getCurrentVersionId() == versionId1) compareArticleList.add(article);
					if(article.getCurrentVersionId() == versionId2) compareArticleList.add(article);
				}
				
//				Article articleVersion1 = (Article) articleList.stream().filter((resp)-> resp.getCurrentVersionId().equals(versionId1));
//				Article articleVersion2 = (Article) articleList.stream().filter((resp)-> resp.getCurrentVersionId().equals(versionId2));
//				
				//System.out.println(compareArticleList.size());
				
				return ResponseEntity.ok(compareArticleList);
			
			}
			
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ArticleDto fromEntityToDto(Article article) {
		User user = this.userFeign.getUserByUserId(article.getUserId()).getBody();
		UserDto userDto = new UserDto(user.getUserId(), user.getUsername(), user.getRole());
		return new ArticleDto(article.getSrNo(), article.getArticleId(), article.getTitle(),  article.getContent(), article.getStatus(), article.getCreationDate(), article.getLastModifiedDate(), article.getCurrentVersionId(), userDto);
	}

	@Override
	public ResponseEntity<List<ArticleDto>> getArticlesByUser(Long userId) {
		try {
			List<Article> articleList = this.articleRepository.findByUserId(userId);
			if(!articleList.isEmpty()) {
				List<ArticleDto> articleDtoList = articleList.stream().map(this::fromEntityToDto).collect(Collectors.toList());
				return ResponseEntity.ok(articleDtoList);
			}
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	

	

}
