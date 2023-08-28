package com.teamsix.workflowservice.repo;

import com.teamsix.workflowservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
}
