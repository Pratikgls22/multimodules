package com.ecommerce.ecom_essentials.service.impl;

import com.ecommerce.ecom_essentials.entities.UserEntity;
import com.ecommerce.ecom_essentials.entities.UserRoleMappingEntity;
import com.ecommerce.ecom_essentials.exception.CustomException;
import com.ecommerce.ecom_essentials.repository.UserRepository;
import com.ecommerce.ecom_essentials.repository.UserRoleMappingRepository;
import com.ecommerce.ecom_essentials.requestDto.LoginRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ResponseTokenDTO;
import com.ecommerce.ecom_essentials.security.JwtTokenProvider;
import com.ecommerce.ecom_essentials.service.LoginService;
import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import com.ecommerce.ecom_essentials.enums.JwtExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMappingRepository userRoleMappingRepository;

    @Override
    public ResponseTokenDTO getToken(LoginRequestDTO loginRequestDTO) {

        //        authenticating user credentials
        this.authenticate(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

//        get the user from the database
        var user = this.getUser(loginRequestDTO.getEmail());

//        comparing the user password and hashed password stored in database
        if (this.passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
//            generating Token
            return getTokenResponse(user);
        } else {
            log.info("Password Not Match : {}", loginRequestDTO.getPassword());
            throw new CustomException(ExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }


    /**
     * @param email <>This method is used to get user from UserRepository</>
     * @return User
     */
    private UserEntity getUser(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED));
    }


    private void authenticate(String email, String password) {
        log.info("Authenticating user with email: {}", email);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.info("Authentication successful for user: {}", email);
        } catch (Exception e) {
            log.error("Exception :: {}", email, e.getMessage());
            throw new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseTokenDTO getTokenResponse(UserEntity userEntity) {
        System.out.println("userEntity.getId() = " + userEntity.getId());
        String userRole;
        Optional<UserRoleMappingEntity> userRoleMappingEntity = this.userRoleMappingRepository.findByUserId(userEntity);
        System.out.println("userRoleMappingEntity = " + userRoleMappingEntity.get());
        if (userRoleMappingEntity.isPresent()) {
            userRole = userRoleMappingEntity.get().getRoleId().getRoleName();
            log.info("userRole ::{}", userRole);
        } else {
            throw new CustomException(ExceptionEnum.USER_ROLE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseTokenDTO(jwtTokenProvider.createToken(userEntity.getEmail(), userRole, userEntity.getId()), userRole, userEntity.getId(), userEntity.getEmail());
        } catch (Exception e) {
            log.info("Exception Catch In Login Service::::");
            throw new CustomException("Error While Creating Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
