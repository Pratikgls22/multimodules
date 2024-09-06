package com.ecommerce.repositry.repositry;

import com.ecommerce.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositry extends JpaRepository<RoleEntity, Long> {
}
