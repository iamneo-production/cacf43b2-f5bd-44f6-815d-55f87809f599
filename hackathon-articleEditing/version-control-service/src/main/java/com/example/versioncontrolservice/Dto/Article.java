package com.example.versioncontrolservice.Dto;

import com.example.versioncontrolservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    private Long srNo;
    private String articleId;
    private String title;
    private String content;
    private Status status;
    private LocalDateTime creationDate;
    private Date lastModifiedDate;
    private Integer currentVersionId;

}
