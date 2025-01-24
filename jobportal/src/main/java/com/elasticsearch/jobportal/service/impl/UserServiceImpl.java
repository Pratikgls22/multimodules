package com.elasticsearch.jobportal.service.impl;

import com.elasticsearch.jobportal.elasticEntity.UserElasticEntity;
import com.elasticsearch.jobportal.elasticRepository.UserElasticRepository;
import com.elasticsearch.jobportal.entity.UserEntity;
import com.elasticsearch.jobportal.repository.UserRepository;
import com.elasticsearch.jobportal.requestDto.UserRequestDto;
import com.elasticsearch.jobportal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserElasticRepository userElasticRepository;

    @Override
    public void createUser(UserRequestDto userRequestDto) {
        try {
            // For Jpa
            UserEntity user = new UserEntity();
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            user.setEmail(userRequestDto.getEmail());
            user.setAddress(userRequestDto.getAddress());
            this.userRepository.save(user);

            // For Elasticsearch
            UserElasticEntity userEs = new UserElasticEntity();
            userEs.setFirstName(userRequestDto.getFirstName());
            userEs.setLastName(userRequestDto.getLastName());
            userEs.setEmail(userRequestDto.getEmail());
            userEs.setAddress(userRequestDto.getAddress());
            this.userElasticRepository.save(userEs);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<UserEntity> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Iterable<UserElasticEntity> getElasticUsers() {
        return this.userElasticRepository.findAll();
    }
}
