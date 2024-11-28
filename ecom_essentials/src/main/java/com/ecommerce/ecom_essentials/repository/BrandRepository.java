package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByKey(String key);

    Optional<BrandEntity> findByBrandName(String brand);
}
