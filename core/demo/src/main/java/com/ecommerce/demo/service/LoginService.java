package com.ecommerce.demo.service;


import com.ecommerce.entity.requestDto.LoginRequestDTO;
import com.ecommerce.entity.responseDto.ResponseTokenDTO;

public interface LoginService {
    ResponseTokenDTO getToken(LoginRequestDTO loginRequestDTO);
}
