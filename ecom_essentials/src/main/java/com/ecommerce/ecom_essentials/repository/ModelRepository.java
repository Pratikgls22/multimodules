package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    Optional<ModelEntity> findByModelName(String modelNode);

    Optional<ModelEntity> findByModelImage(String modelImage);
}
