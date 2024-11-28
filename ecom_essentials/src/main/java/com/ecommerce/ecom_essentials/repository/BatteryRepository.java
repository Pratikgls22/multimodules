package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BatteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatteryRepository extends JpaRepository<BatteryEntity,Long> {
    Optional<BatteryEntity> findByBatteryCapacity(String batteryCapacity);
}
