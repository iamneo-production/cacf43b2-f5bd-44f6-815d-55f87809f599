package com.teamsix.workflowservice.controller;

import com.teamsix.workflowservice.entity.Article;
import com.teamsix.workflowservice.entity.User;
import com.teamsix.workflowservice.repo.ArticleRepo;
import com.teamsix.workflowservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/user-service")
public class UserController {
    @Autowired
    UserRepo userRepo;
    @PostMapping("/user")
    public User saveUser(@RequestBody User user){
        System.out.println(user);
        return userRepo.save(user);
    }
    @GetMapping("/user")
    public List<User> getUsers(){
        return userRepo.findAll();
    }
    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable String userId){
        return userRepo.findById(userId).get();
    }
}
