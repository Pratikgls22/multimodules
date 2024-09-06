package com.ecommerce.jwtsecurity.security;

import com.ecommerce.model.entity.UserEntity;
import com.ecommerce.model.entity.UserRoleEntity;
import com.ecommerce.repositry.repositry.UserRepositry;
import com.ecommerce.repositry.repositry.UserRoleRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private UserRoleRepositry userRoleRepositry;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        Optional< UserEntity>  user = this.userRepositry.findByUserEmail(useremail);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found: "+ useremail);
        }
        Optional<UserRoleEntity> userRole = userRoleRepositry.findByUser(user.get());

        return new MyUserDetails(user.get(),userRole.orElse(null));
    }
}
