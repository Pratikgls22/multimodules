package com.job.females.in.tech.repository;

import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {

    Optional<UserLoginEntity> findByUser(UserEntity user);
}
