package com.ecommerce.demo.controller;


import com.ecommerce.demo.service.LoginService;
import com.ecommerce.entity.requestDto.LoginRequestDTO;
import com.ecommerce.entity.responseDto.ApiResponse;
import com.ecommerce.entity.responseDto.ResponseTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
@CrossOrigin("http://localhost:9093")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> getToken(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        log.info("Login User Details::::::", loginRequestDTO);
        ResponseTokenDTO responseTokenDTO = this.loginService.getToken(loginRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Logged In User", responseTokenDTO), HttpStatus.OK);

    }
}
