package com.ecommerce.repositry.repositry;

import com.ecommerce.model.entity.UserEntity;
import com.ecommerce.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepositry extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByUser(UserEntity user);
}
