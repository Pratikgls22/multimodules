package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity,Long> {
    Optional<PriceEntity> findByPrice(String text);
}
