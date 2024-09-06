package com.ecommerce.jwtsecurity.security;


import com.ecommerce.model.entity.UserEntity;
import com.ecommerce.model.entity.UserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MyUserDetails implements UserDetails {


    private String userEmail;
    private String password;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(UserEntity userEntity, UserRoleEntity userRole) {
        this.userEmail = userEntity.getUserEmail();
        this.password = userEntity.getUserPassword();
        authorities = Arrays.asList(new SimpleGrantedAuthority(userRole.getRoles().getRoleName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        System.out.println("**************MyUserDetails**********"+ userEmail);
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
