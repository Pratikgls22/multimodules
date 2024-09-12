package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity,Long> {
    Optional<ModelEntity> findByName(String modelName);
}
