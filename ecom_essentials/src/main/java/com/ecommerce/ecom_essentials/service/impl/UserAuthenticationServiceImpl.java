package com.ecommerce.ecom_essentials.service.impl;

import com.ecommerce.ecom_essentials.entities.UserEntity;
import com.ecommerce.ecom_essentials.entities.UserRoleMappingEntity;
import com.ecommerce.ecom_essentials.repository.UserRepository;
import com.ecommerce.ecom_essentials.repository.UserRoleMappingRepository;
import com.ecommerce.ecom_essentials.service.UserAuthenticationService;
import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;


    @Override
    public Optional<UserDetails> findUserByEmail(String username) {
        System.out.println("username in userAuthrnticationImpl = " + username);
        UserEntity userEntity = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionEnum.USER_NOT_FOUND.getValue()));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        UserRoleMappingEntity userRoleMappingEntity =  this.userRoleMappingRepository.findByUserId(userEntity).orElseThrow(() -> new RuntimeException(ExceptionEnum.USER_NOT_FOUND.getValue()));

        grantedAuthorities.add(new SimpleGrantedAuthority(userRoleMappingEntity.getRoleId().getRoleName()));

        return Optional.of(new User(userEntity.getEmail(),userEntity.getPassword(),grantedAuthorities));
    }
}
