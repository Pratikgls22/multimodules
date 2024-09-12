package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity,Long> {
}
