package com.teamsix.workflowservice.openFeign;

import com.teamsix.workflowservice.entity.Article;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ARTICLE-SERVICE",path = "api/article")
public interface ArticleFeign {
    @GetMapping("/{articleId}")
    public ResponseEntity<List<Article>> getArticleById(@PathVariable String articleId);

}
