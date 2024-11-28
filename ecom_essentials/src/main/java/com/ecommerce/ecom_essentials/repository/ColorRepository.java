package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity,Long> {
    Optional<ColorEntity> findByColor(String text);
}
