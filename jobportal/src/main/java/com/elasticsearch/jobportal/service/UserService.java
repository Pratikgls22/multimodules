package com.elasticsearch.jobportal.service;

import com.elasticsearch.jobportal.elasticEntity.UserElasticEntity;
import com.elasticsearch.jobportal.entity.UserEntity;
import com.elasticsearch.jobportal.requestDto.UserRequestDto;

import java.util.List;

public interface UserService {

    void createUser(UserRequestDto userRequestDto);

    List<UserEntity> getUsers();

    Iterable<UserElasticEntity> getElasticUsers();
}
