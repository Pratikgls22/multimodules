package com.job.females.in.tech.controller;

import com.job.females.in.tech.enums.ControllerEnums;
import com.job.females.in.tech.requestDto.EmployerRequestDto;
import com.job.females.in.tech.responseDto.ApiResponse;
import com.job.females.in.tech.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/employer")
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ApiResponse> registerEmployer(@Valid @RequestBody EmployerRequestDto employerRequestDto){
        log.info("Register employer request - save :: {}", employerRequestDto);
        this.employerService.registerEmployer(employerRequestDto);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, ControllerEnums.EMPLOYER_ADDED_SUCCESSFULLY.getValue(), new HashMap<>()),HttpStatus.OK);
    }
}
