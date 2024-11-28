package com.ecommerce.ecom_essentials.controller;

import com.ecommerce.ecom_essentials.requestDto.LoginRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ApiResponse;
import com.ecommerce.ecom_essentials.responseDto.ResponseTokenDTO;
import com.ecommerce.ecom_essentials.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> getToken(@Valid  @RequestBody  LoginRequestDTO loginRequestDTO){
        log.info("Login User Details::::::", loginRequestDTO);
        ResponseTokenDTO responseTokenDTO = this.loginService.getToken(loginRequestDTO);
        System.out.println("responseTokenDTO controller = " + responseTokenDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User Login successFully",responseTokenDTO),HttpStatus.OK);
    }

    @GetMapping("/get")
    public String getData(){
        return "Ecommerce Essentails ready to work on Docker";
    }

}
