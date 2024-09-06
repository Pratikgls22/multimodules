package com.ecommerce.services.service.impl;

import com.ecommerce.jwtsecurity.security.JwtTokenProvider;
import com.ecommerce.model.dto.AuthRequest;
import com.ecommerce.model.dto.ResponseTokenDto;
import com.ecommerce.model.entity.UserEntity;
import com.ecommerce.model.entity.UserRoleEntity;
import com.ecommerce.repositry.repositry.UserRepositry;
import com.ecommerce.repositry.repositry.UserRoleRepositry;
import com.ecommerce.services.service.LoginService;
import com.ecommerce.utility.enums.ExceptionEnum;
import com.ecommerce.utility.enums.JwtExceptionEnum;
import com.ecommerce.utility.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginServiceImpl implements LoginService {

    private final JwtTokenProvider jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepositry userRepositry;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepositry userRoleRepositry;

    @Autowired
    public LoginServiceImpl(JwtTokenProvider jwtService, AuthenticationManager authenticationManager, UserRepositry userRepositry, PasswordEncoder passwordEncoder, UserRoleRepositry userRoleRepositry) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepositry = userRepositry;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepositry = userRoleRepositry;
    }

    @Override
    public ResponseTokenDto getToken(AuthRequest authRequest) {
        this.authenticate(authRequest.getUseremail(),authRequest.getPassword());

        var user = this.getUserForToken(authRequest.getUseremail());

        if(this.passwordEncoder.matches(authRequest.getPassword(),user.getUserPassword())){
            return getTokenResponse(user);
        }
        else {
            throw new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }


    private void authenticate(String useremail, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(useremail, password));
        } catch (AuthenticationException e) {
            throw new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }


    private UserEntity getUserForToken(String useremail) {
        return this.userRepositry.findByUserEmail(useremail)
                .orElseThrow(()-> new CustomException(JwtExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD.getValue(), HttpStatus.UNAUTHORIZED));
    }

    private ResponseTokenDto getTokenResponse(UserEntity user) {

        Optional<UserRoleEntity> userRoleEntity = this.userRoleRepositry.findByUser(user);

        String userRole;
        if (userRoleEntity.isPresent()) {
            userRole = userRoleEntity.get().getRoles().getRoleName();
        } else {
            throw new CustomException(ExceptionEnum.USER_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseTokenDto(jwtService.createToken(user.getUserEmail(),user.getUserName()),userRole,user.getId());
        } catch (Exception e) {
            throw new CustomException("Error while Create Token",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
