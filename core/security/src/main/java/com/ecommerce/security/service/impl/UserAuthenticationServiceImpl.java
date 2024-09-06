package com.ecommerce.security.service.impl;

import com.ecommerce.entity.entity.UserEntity;
import com.ecommerce.entity.entity.UserRoleMappingEntity;
import com.ecommerce.repository.repository.UserRepository;
import com.ecommerce.repository.repository.UserRoleMappingRepository;
import com.ecommerce.security.service.UserAuthenticationService;
import com.ecommerce.utility.enums.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserRepository userRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;

    @Override
    public Optional<UserDetails> findUserByEmail(String userName) {

        UserEntity userEntity = this.userRepository.findByEmail(userName).orElseThrow(() -> new RuntimeException(ExceptionEnum.USER_NOT_FOUND.getValue()));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        UserRoleMappingEntity userRoleMappingEntity = this.userRoleMappingRepository.findByUserId(userEntity).orElseThrow(() -> new RuntimeException(ExceptionEnum.USER_NOT_FOUND.getValue()));

        grantedAuthorities.add(new SimpleGrantedAuthority(userRoleMappingEntity.getRoleId().getRole()));

        return Optional.of(new User(userEntity.getUserName(), userEntity.getPassword(), grantedAuthorities));
    }
}
