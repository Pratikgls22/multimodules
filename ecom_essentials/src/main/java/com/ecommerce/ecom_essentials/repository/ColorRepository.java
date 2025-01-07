package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity,Long> {

    Optional<ColorEntity> findByColor(String text);

    @Query(value = "select ce.color from color_entity ce ;",nativeQuery = true)
    List<String> findByColor();
}
