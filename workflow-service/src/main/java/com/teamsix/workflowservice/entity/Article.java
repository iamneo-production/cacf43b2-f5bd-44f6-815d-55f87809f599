package com.teamsix.workflowservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Article {
    @Id
    //@GeneratedValue
    @UuidGenerator
    private String articleId;
    private String title;
    private String content;
    private String creationDate;
    private String lastModificationDate;
    private String currentVersionId;
    private String userId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getCurrentVersionId() {
        return currentVersionId;
    }

    public void setCurrentVersionId(String currentVersionId) {
        this.currentVersionId = currentVersionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Article() {
    }

    public Article(String articleId, String title, String content, String creationDate, String lastModificationDate, String currentVersionId, String userId) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.currentVersionId = currentVersionId;
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModificationDate='" + lastModificationDate + '\'' +
                ", currentVersionId='" + currentVersionId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
