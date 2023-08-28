package com.example.versioncontrolservice.service;

import com.example.versioncontrolservice.Dto.Article;
import com.example.versioncontrolservice.Dto.VersionTrackingDto;
import com.example.versioncontrolservice.Dto.VersionTrackingResponse;

import java.util.List;

public interface VersionTrackingService {


    List<Article> trackVersionByArticleId(String arcticleId);


    List<Article> compareArticle(String articleId, Integer versionId1, Integer versionId2);

    VersionTrackingResponse createVersion(String articleId, Article article);
}
