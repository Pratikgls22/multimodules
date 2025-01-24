package com.job.females.in.tech.controller;

import com.job.females.in.tech.enums.ControllerEnums;
import com.job.females.in.tech.requestDto.LoginRequestDto;
import com.job.females.in.tech.responseDto.ApiResponse;
import com.job.females.in.tech.responseDto.ResponseTokenDto;
import com.job.females.in.tech.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /***
     * Generates an authentication token for the user
     *
     * @param loginRequestDto the login request containing the user's email and password.
     * @return a response containing the generated token if the login credentials are valid.
     */
    @PostMapping(value = "/getToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getToken(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        ResponseTokenDto responseTokenDto = this.loginService.getToken(loginRequestDto);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, ControllerEnums.TOKEN_CREATED.getValue(), responseTokenDto), HttpStatus.OK);
    }

    /**
     * Retrieves an authentication token for the user.
     *
     * @param userEmail the email address of the user.
     * @param password  the user's password.
     * @return a response containing the generated token if the email and password are valid.
     */
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getToken(@RequestParam(value = "userEmail", required = false) String userEmail,
                                                @RequestParam(value = "password", required = false) String password) {
        var responseTokenDto = this.loginService.getTokenResponse(userEmail, password);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, ControllerEnums.GET_TOKEN_SUCCESSFULLY.getValue(), responseTokenDto), HttpStatus.OK);
    }
}
