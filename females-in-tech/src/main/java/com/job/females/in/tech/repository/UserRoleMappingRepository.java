package com.job.females.in.tech.repository;

import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.entity.UserRoleMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMappingEntity, Long> {

    Optional<UserRoleMappingEntity> findByUserId(UserEntity userId);
}
