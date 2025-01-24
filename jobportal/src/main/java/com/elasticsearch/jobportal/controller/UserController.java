package com.elasticsearch.jobportal.controller;

import com.elasticsearch.jobportal.elasticEntity.UserElasticEntity;
import com.elasticsearch.jobportal.entity.UserEntity;
import com.elasticsearch.jobportal.requestDto.UserRequestDto;
import com.elasticsearch.jobportal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/createUsers")
    public String createUser(@RequestBody UserRequestDto userRequestDto){
        this.userService.createUser(userRequestDto);
        return "User Created Successfully";
    }

    @GetMapping(value = "/getUsers")
    public List<UserEntity> getUsers(){
        var response = this.userService.getUsers();
        return response;
    }

    @GetMapping(value = "/getElasticUsers")
    public Iterable<UserElasticEntity> getElasticUsers(){
        var response = this.userService.getElasticUsers();
        return response;
    }
}
