package com.job.females.in.tech.service.impl;

import com.job.females.in.tech.entity.RoleEntity;
import com.job.females.in.tech.entity.UserDetailsEntity;
import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.entity.UserRoleMappingEntity;
import com.job.females.in.tech.enums.CommonEnum;
import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.exception.CustomException;
import com.job.females.in.tech.repository.*;
import com.job.females.in.tech.requestDto.UserRequestDto;
import com.job.females.in.tech.service.EmailService;
import com.job.females.in.tech.service.UserService;
import com.job.females.in.tech.utility.BaseMethod;
import com.job.females.in.tech.utility.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BaseMethod baseMethod;
    private final Utilities utilities;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    String resetUrl = "${login.url}";

    @Override
    public void forgotPassword(String userEmail) {

        // Check User Email is Exist or not
        var user = baseMethod.getUser(userEmail);
        log.info("User : {}", user);

        if (!user.isActive()) {
            log.info("user status: {}", false);
            throw new CustomException(ExceptionEnum.ACCOUNT_DISABLED.getValue(), HttpStatus.NOT_FOUND);
        }

        if (user.getAccountLock().equals(Boolean.TRUE)) {
            log.info("user accountLock status : {}", user.getAccountLock());
            throw new CustomException(ExceptionEnum.ACCOUNT_LOCKED.getValue(), HttpStatus.LOCKED);
        }

        String link = resetUrl + "account/verify" + "?" + "userName" + "=" + userEmail + "&" + "token" + "=";

        // Message Body
        String body = baseMethod.getHtmlCode(userEmail, link);

        emailService.sendMail(userEmail, body);
    }

    public UserEntity currentUser(){
        return utilities.getCurrentEntity(UserEntity.class, this.userRepository::findById);
    }

    @Override
    public void registerUser(UserRequestDto userRequestDto) {
        try {
            // Check user is existing or not
            var user = this.userRepository.findByEmailIgnoreCaseAndIsDeleteFalse(userRequestDto.getEmail());
            log.info("User already existed : {} ", user);

            if (user.isPresent()) {
                log.error("User already exists with email : {}", userRequestDto.getEmail());
                throw new CustomException(ExceptionEnum.USER_ALREADY_EXIST.getValue(), HttpStatus.BAD_REQUEST);
            }

            // Fetch Current User
//            UserEntity currentUser = utilities.currentUser();
            UserEntity currentUser = currentUser();
            log.info("Current User : {}", currentUser);

            // set values in user entity and save
            UserEntity userEntity = getUserEntity(userRequestDto, currentUser);
            this.userRepository.save(userEntity);
            log.info("userEntity values saved : {}", userEntity);

            // set values in user details entity and save
            UserDetailsEntity userDetailsEntity = getUserDetailsEntity(userRequestDto, currentUser, userEntity);
            this.userDetailsRepository.save(userDetailsEntity);

            // fetch role entity with role name
            RoleEntity roleEntity = this.roleRepository.findByRoleName(CommonEnum.NEW_USER.getValue())
                    .orElseThrow(() -> new CustomException(ExceptionEnum.ROLE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND));

            // save user role mapping entity with 'NEW_USER' role
            UserRoleMappingEntity userRoleMappingEntity = this.baseMethod.getUserRoleMappingEntity(userEntity, roleEntity);
            this.userRoleMappingRepository.save(userRoleMappingEntity);

        } catch (CustomException e) {
            log.error("Error while registering user : {}", e.getMessage());
            throw new CustomException(ExceptionEnum.ERROR_WHILE_REGISTERING_USER.getValue() + e.getMessage(), e.getHttpStatus());
        }

    }

    private UserDetailsEntity getUserDetailsEntity(UserRequestDto userRequestDto, UserEntity currentUser, UserEntity userEntity) {
        return UserDetailsEntity.builder()
                .experienceLevel(userRequestDto.getExperienceLevel())
                .eduction(userRequestDto.getEduction())
                .dob(userRequestDto.getDob())
                .userId(userEntity)
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .build();
    }

    private UserEntity getUserEntity(UserRequestDto userRequestDto, UserEntity currentUser) {
        return UserEntity.builder()
                .userName(userRequestDto.getUserName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .loginAttempt(0L)
                .accountLock(Boolean.FALSE)
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .build();
    }
}
