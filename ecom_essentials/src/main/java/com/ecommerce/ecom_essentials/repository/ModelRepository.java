package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity,Long> {

    Optional<ModelEntity> findByKey(String key);


    @Query(value = "SELECT me.device_name FROM model_entity AS me " +
            "JOIN brand_entity AS be ON be.id = me.brand_id " +
            "WHERE be.brand_name LIKE CONCAT('%', :brand, '%')",
            nativeQuery = true)
    List<String> findDeviceNamesByBrand(@Param("brand") String brand);

    Optional<ModelEntity> findByDeviceName(String modelName);
}
