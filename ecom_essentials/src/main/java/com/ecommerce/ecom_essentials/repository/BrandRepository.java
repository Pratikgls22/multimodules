package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByBrandName(String brand);

    @Query(value = " select be.brand_name from brand_entity as be ; ",nativeQuery = true)
    List<String> findByBrand();
}
