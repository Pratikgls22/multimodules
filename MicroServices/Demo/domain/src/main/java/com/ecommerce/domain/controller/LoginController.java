package com.ecommerce.domain.controller;

import com.ecommerce.model.dto.ApiResponse;
import com.ecommerce.model.dto.AuthRequest;
import com.ecommerce.model.dto.ResponseTokenDto;
import com.ecommerce.services.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login/")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/gettoken")
    public ResponseEntity<ApiResponse> getToken(@RequestBody AuthRequest authRequest){
        ResponseTokenDto responseToken = this.loginService.getToken(authRequest);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User Login Successfully",responseToken),HttpStatus.OK);
    }

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String home(){
        return "Home Method Called";
    }
}
