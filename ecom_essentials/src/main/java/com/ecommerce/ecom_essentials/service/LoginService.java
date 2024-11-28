package com.ecommerce.ecom_essentials.service;

import com.ecommerce.ecom_essentials.requestDto.LoginRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ResponseTokenDTO;


public interface LoginService {
    ResponseTokenDTO getToken(LoginRequestDTO loginRequestDTO);

}
