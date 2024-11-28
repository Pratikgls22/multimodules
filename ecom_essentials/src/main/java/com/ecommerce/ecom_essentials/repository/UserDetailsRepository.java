package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.UserDetailsEntity;
import com.ecommerce.ecom_essentials.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {

    Optional<UserDetailsEntity> findByUserId(UserEntity id);
}
