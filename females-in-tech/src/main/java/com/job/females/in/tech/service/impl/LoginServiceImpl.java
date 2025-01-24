package com.job.females.in.tech.service.impl;

import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.entity.UserLoginEntity;
import com.job.females.in.tech.entity.UserRoleMappingEntity;
import com.job.females.in.tech.enums.CommonEnum;
import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.enums.JwtExceptionEnum;
import com.job.females.in.tech.exception.CustomException;
import com.job.females.in.tech.jwt.JwtProvider;
import com.job.females.in.tech.repository.UserLoginRepository;
import com.job.females.in.tech.repository.UserRepository;
import com.job.females.in.tech.repository.UserRoleMappingRepository;
import com.job.females.in.tech.requestDto.LoginRequestDto;
import com.job.females.in.tech.responseDto.ResponseTokenDto;
import com.job.females.in.tech.service.LoginService;
import com.job.females.in.tech.utility.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final AuthenticationManager authenticationManager;
    private final UserLoginRepository userLoginRepository;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseTokenDto getToken(LoginRequestDto loginRequestDto) {

        // authenticating user credentials
        this.authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // get the user from the database
        var user = this.getUser(loginRequestDto.getEmail());

        if (this.passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return tokenResponse(user);
        } else {
            log.info("Password Not Match : {}", loginRequestDto.getPassword());
            throw new CustomException(ExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public String getTokenResponse(String userEmail, String password) {

        // Fetch the user
        UserEntity user = getUser(userEmail);
        log.info("user of tokenResponse : {}", userEmail);

        // Fetch user login details and validate token
        UserLoginEntity userLoginEntity = validateUserLogin(user, password);
        log.info("userLoginEntity of tokenResponse : {}", userLoginEntity );

        // Generate a new password
        String newPassword = Utilities.generateStrongPassword(12);
        log.info("newPassword of tokenResponse: {}", newPassword);

        // Update and save user login and password
        userLoginEntity.setIsUsed(true);
        this.userLoginRepository.save(userLoginEntity);

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLoginAttempt(0L);
        user.setLastModifiedDate(Utilities.getDate());
        user.setCreateDate(Utilities.getDate());
        user.setLastLogin(Utilities.getDate());
        this.userRepository.save(user);

        return CommonEnum.RESET_PASSWORD_OK.getValue();
    }

    private UserLoginEntity validateUserLogin(UserEntity user, String password) {
        UserLoginEntity userLogin = this.userLoginRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED));
        System.out.println("userLogin of validateUserLogin method= " + userLogin);

        if (Boolean.TRUE.equals(userLogin.getIsUsed())){
            throw new CustomException(ExceptionEnum.INVALID_RESET_PASSWORD_LINK.getValue(), HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(password, userLogin.getPassword())){
            incrementLoginAttempt(user);
        }
        return userLogin;
    }

    private void incrementLoginAttempt(UserEntity user) {
        user.setLastModifiedDate(Utilities.getDate());
        user.setLoginAttempt(user.getLoginAttempt() + 1 );
        userRepository.save(user);
    }


    private UserEntity getUser(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }

    private void authenticate(String email, String password) {
        log.info("Authenticating user with password: {}", password);
        try {
            System.out.println("email = " + email);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.info("Authentication successful for user: {}", email);
        } catch (Exception e) {
            log.error("Exception with email in LoginServiceImpl : {}", e.getMessage());
            throw new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseTokenDto tokenResponse(UserEntity userEntity) {
        String userRole;
        Optional<UserRoleMappingEntity> userRoleMapping = this.userRoleMappingRepository.findByUserId(userEntity);
        if (userRoleMapping.isEmpty()) {
            throw new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        userRole = userRoleMapping.get().getRoleId().getRoleName();
        log.info("userRole : {}", userRole);

        try {
            return new ResponseTokenDto(jwtProvider.createToken(userEntity.getEmail(), userRole, userEntity.getId()), userRole, userEntity.getId(), userEntity.getEmail());
        } catch (Exception e) {
            log.info("Exception Catch In Login Service::::");
            throw new CustomException(ExceptionEnum.ERROR_WHILE_CREATING_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
