package com.job.females.in.tech.service.impl;

import com.job.females.in.tech.entity.EmployerEntity;
import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.exception.CustomException;
import com.job.females.in.tech.repository.EmployerRepository;
import com.job.females.in.tech.requestDto.EmployerRequestDto;
import com.job.females.in.tech.service.EmployerService;
import com.job.females.in.tech.utility.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final Utilities utilities;

    public EmployerEntity currentEmployer(){
        return utilities.getCurrentEntity(EmployerEntity.class , this.employerRepository::findById);
    }

    @Override
    public void registerEmployer(EmployerRequestDto employerRequestDto) {
        log.info("Registering employer request : {}", employerRequestDto);

        // Check Employer Exist or not
        var employer = this.employerRepository.findByCompanyEmailIgnoreCaseAndIsDeleteFalse(employerRequestDto.getCompanyEmail());

        if (employer.isPresent()){
            log.error("User already exists with email : {}", employerRequestDto.getCompanyEmail());
            throw new CustomException(ExceptionEnum.USER_ALREADY_EXIST.getValue(), HttpStatus.BAD_REQUEST);
        }

        // Fetch Current Employer
//        UserEntity currentEmployer = utilities.currentUser();
        EmployerEntity currentEmployer = currentEmployer();
        log.info("Current Employer : {}", currentEmployer);

        EmployerEntity employerEntity = getEmployerEntity(employerRequestDto, currentEmployer);
        this.employerRepository.save(employerEntity);

    }

    //  Method for get new employer
    private static EmployerEntity getEmployerEntity(EmployerRequestDto employerRequestDto, EmployerEntity currentEmployer) {
        EmployerEntity employerEntity = new EmployerEntity();
        employerEntity.setCompanyEmail(employerRequestDto.getCompanyEmail());
        employerEntity.setCompanyName(employerRequestDto.getCompanyName());
        employerEntity.setCompanyPhone(employerRequestDto.getCompanyPhone());
        employerEntity.setCompanyType(employerRequestDto.getCompanySize());
        employerEntity.setCompanySize(employerRequestDto.getCompanySize());
        employerEntity.setLocation(employerRequestDto.getLocation());
        employerEntity.setCreatedBy(currentEmployer.getCreatedBy());
        employerEntity.setUpdatedBy(currentEmployer.getUpdatedBy());

        return employerEntity;
    }

}
