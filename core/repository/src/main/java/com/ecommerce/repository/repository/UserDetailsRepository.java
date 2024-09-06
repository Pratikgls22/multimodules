package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.UserDetailsEntity;
import com.ecommerce.entity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {


  Optional<UserDetailsEntity> findByUserId(UserEntity userEntity);


}
