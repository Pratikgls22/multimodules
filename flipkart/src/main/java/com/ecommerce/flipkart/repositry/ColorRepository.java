package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity,Long> {
    Optional<ColorEntity> findByName(String colorName);
}
