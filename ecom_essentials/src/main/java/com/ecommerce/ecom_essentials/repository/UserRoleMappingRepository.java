package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.UserEntity;
import com.ecommerce.ecom_essentials.entities.UserRoleMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMappingEntity,Long> {

    Optional<UserRoleMappingEntity> findByUserId(UserEntity userEntity);
}
