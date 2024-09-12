package com.ecommerce.demo.service.impl;


import com.ecommerce.demo.service.LoginService;
import com.ecommerce.entity.entity.UserEntity;
import com.ecommerce.entity.entity.UserRoleMappingEntity;
import com.ecommerce.entity.requestDto.LoginRequestDTO;
import com.ecommerce.entity.responseDto.ResponseTokenDTO;
import com.ecommerce.repository.repository.UserRepository;
import com.ecommerce.repository.repository.UserRoleMappingRepository;
import com.ecommerce.security.security.jwt.JwtTokenProvider;
import com.ecommerce.utility.enums.ExceptionEnum;
import com.ecommerce.utility.enums.JwtExceptionEnum;
import com.ecommerce.utility.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
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
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            log.error("Exception :: {}", e.getMessage());
            throw new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseTokenDTO getTokenResponse(UserEntity userEntity) {
        String userRole;
        Optional<UserRoleMappingEntity> userRoleMappingEntity = this.userRoleMappingRepository.findByUserId(userEntity);

        if (userRoleMappingEntity.isPresent()) {
            userRole = userRoleMappingEntity.get().getRoleId().getRole();
            log.info("userRole ::{}", userRole);
        } else {
            throw new CustomException(ExceptionEnum.USER_ROLE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseTokenDTO(jwtTokenProvider.createToken(userEntity.getEmail(), userRole), userRole, userEntity.getId(), userEntity.getUserName());
        } catch (Exception e) {
            log.info("Exception Catch In Login Service::::");
            throw new CustomException("Error While Creating Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
