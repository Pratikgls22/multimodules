package com.ecommerce.ecom_essentials.utility;

import com.ecommerce.ecom_essentials.entities.UserEntity;
import com.ecommerce.ecom_essentials.repository.UserRepository;
import com.ecommerce.ecom_essentials.responseDto.TokenClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Utilities {
        private final UserRepository userRepository;
        private final TokenClaims tokenClaims;


        public UserEntity currentUser(){
            return Optional.ofNullable(this.tokenClaims.getUserId())
                    .flatMap(this.userRepository::findById)
                    .orElse(null);
        }

}
