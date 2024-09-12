package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.RamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RamRepository extends JpaRepository<RamEntity,Long> {
    Optional<RamEntity> findBySize(String ramSize);
}
