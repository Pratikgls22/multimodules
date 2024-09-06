package com.ecommerce.services.service;

import com.ecommerce.model.dto.AuthRequest;
import com.ecommerce.model.dto.ResponseTokenDto;

public interface LoginService {

    ResponseTokenDto getToken(AuthRequest authRequest);
}
