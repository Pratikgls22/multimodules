package com.job.females.in.tech.service.impl;

import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.entity.UserRoleMappingEntity;
import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.repository.UserRepository;
import com.job.females.in.tech.repository.UserRoleMappingRepository;
import com.job.females.in.tech.service.UserAuthenticationService;
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
        UserEntity userEntity = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionEnum.USER_NOT_FOUND.getMessage()));

        UserRoleMappingEntity userRoleMappingEntity = this.userRoleMappingRepository.findByUserId(userEntity)
                .orElseThrow(() -> new RuntimeException(ExceptionEnum.USER_NOT_FOUND.getValue()));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(userRoleMappingEntity.getRoleId().getRoleName()));

        return Optional.of(new User(userEntity.getEmail(),userEntity.getPassword(),grantedAuthorities));
    }
}
