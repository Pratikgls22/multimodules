package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BatteryEntity;
import com.ecommerce.ecom_essentials.entities.BrandEntity;
import com.ecommerce.ecom_essentials.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatteryRepository extends JpaRepository<BatteryEntity,Long> {

    Optional<BatteryEntity> findByBatteryCapacity(String batteryCapacity);

    @Query(value = "select be.battery_capacity from battery_entity as be", nativeQuery = true)
    List<String> findByBattery();

//    Optional<BatteryEntity> findByBatteryCapacityAndBrandIdAndModelId(String text, BrandEntity brand, ModelEntity modelEntity);
}

