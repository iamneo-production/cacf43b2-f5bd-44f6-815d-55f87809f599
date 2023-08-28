package com.teamsix.workflowservice.repo;

import com.teamsix.workflowservice.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article,String> {
}
