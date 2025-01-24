package com.job.females.in.tech.service;

import com.job.females.in.tech.requestDto.LoginRequestDto;
import com.job.females.in.tech.responseDto.ResponseTokenDto;

public interface LoginService {

    ResponseTokenDto getToken(LoginRequestDto loginRequestDto);

    String getTokenResponse(String userEmail, String password);
}
