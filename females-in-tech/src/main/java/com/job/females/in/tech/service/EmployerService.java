package com.job.females.in.tech.service;


import com.job.females.in.tech.requestDto.EmployerRequestDto;
import jakarta.validation.Valid;

public interface EmployerService {
    void registerEmployer(@Valid EmployerRequestDto employerRequestDto);
}
