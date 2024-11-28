package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String roleName);

//    List<RoleEntity> findAllActiveRoll();
}
