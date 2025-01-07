package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import com.ecommerce.ecom_essentials.entities.ModelEntity;
import com.ecommerce.ecom_essentials.entities.OperatingSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperatingSystemRepository extends JpaRepository<OperatingSystemEntity, Long> {

    @Query(value = "SELECT * FROM operating_system_entity ose WHERE ose.operating_system LIKE :operatingSystem% LIMIT 1", nativeQuery = true)
    Optional<OperatingSystemEntity> findByOperatingSystem(@Param("operatingSystem") String operatingSystem);

    @Query(value = "select ose.operating_system color from operating_system_entity ose ;",nativeQuery = true)
    List<String> findByOS();

//    Optional<OperatingSystemEntity> findByOperatingSystemAndBrandIdAndModelId(String text, BrandEntity brand, ModelEntity modelEntity);
}
