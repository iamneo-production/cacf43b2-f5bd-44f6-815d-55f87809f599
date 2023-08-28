package com.teamsix.workflowservice.repo;

import com.teamsix.workflowservice.entity.WorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRepo extends JpaRepository<WorkFlow,String> {
    public WorkFlow findByArticleId(String articleId);
}
