package com.job.females.in.tech.service;


import com.job.females.in.tech.requestDto.UserRequestDto;
import jakarta.validation.Valid;

public interface UserService {

    void forgotPassword(String userEmail);

    void registerUser(@Valid UserRequestDto userRequestDto);
}
