package com.job.females.in.tech.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserAuthenticationService {

    Optional<UserDetails> findUserByEmail(String username);
}
