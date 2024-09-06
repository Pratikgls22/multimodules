package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.UserEntity;
import com.ecommerce.entity.entity.UserRoleMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMappingEntity, Long> {
  Optional<UserRoleMappingEntity> findByUserId(UserEntity userEntity);

}
