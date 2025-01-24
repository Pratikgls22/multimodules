package com.job.females.in.tech.controller;

import com.job.females.in.tech.enums.ControllerEnums;
import com.job.females.in.tech.requestDto.EmployerRequestDto;
import com.job.females.in.tech.requestDto.UserRequestDto;
import com.job.females.in.tech.responseDto.ApiResponse;
import com.job.females.in.tech.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Sends a password reset link to the user's email.
     *
     * @param userEmail the email address of the user who wants to reset their password.
     * @return a response indicating that the reset password link was sent successfully.
     */
    @PostMapping(value = "/forgotPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam(value = "userEmail", required = false) String userEmail) {
        this.userService.forgotPassword(userEmail);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, ControllerEnums.RESET_PASSWORD_LINK_SENT_SUCCESSFULLY.getValue(), new HashMap<>()),HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ApiResponse> registerEmployer(@Valid @RequestBody UserRequestDto userRequestDto){
        log.info("Register user request - save :: {}", userRequestDto);
        this.userService.registerUser(userRequestDto);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, ControllerEnums.EMPLOYER_ADDED_SUCCESSFULLY.getValue(), new HashMap<>()),HttpStatus.OK);
    }
}
