package com.teamsix.workflowservice.controller;

import com.teamsix.workflowservice.entity.Article;
import com.teamsix.workflowservice.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article-service")
public class ArticleController {
    @Autowired
    ArticleRepo articleRepo;
     @PostMapping("/article")
    public Article saveArticle(@RequestBody Article article){
     return articleRepo.save(article);
    }
    @GetMapping("/article")
    public List<Article> getArticles(){
    return articleRepo.findAll();
    }
    @GetMapping("/article/{articleId}")
    public Article getArticleById(@PathVariable String articleId){
        return articleRepo.findById(articleId).get();
    }

}
