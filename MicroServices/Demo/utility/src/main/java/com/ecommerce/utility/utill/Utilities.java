package com.ecommerce.utility.utill;

import com.ecommerce.model.dto.TokenClaims;
import com.ecommerce.model.entity.UserEntity;
import com.ecommerce.repositry.repositry.UserRepositry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class Utilities {

    private final UserRepositry userRepositry;

    @Autowired
    private TokenClaims tokenClaims;

    public UserEntity getCurrentUSer(){
        return Optional.ofNullable(this.tokenClaims.getUserId())
                .flatMap(this.userRepositry::findById)
                .orElse(null);
  }

}
