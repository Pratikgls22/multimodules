package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    Optional<ModelEntity> findByModelName(String modelNode);

    Optional<ModelEntity> findByModelImage(String modelImage);


    @Query(value = "select me.model_name, me.brand_id  from model_entity as me \n" +
            "join brand_entity as be on be.id = me.brand_id \n" +
            "where be.brand_name = 'Sony';", nativeQuery = true)
    List<String> findByBrandId_BrandName(String brandIdBrandName);
}
