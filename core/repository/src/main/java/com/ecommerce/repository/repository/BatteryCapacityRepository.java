package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.BatteryCapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatteryCapacityRepository extends JpaRepository<BatteryCapacityEntity,Long> {
    Optional<BatteryCapacityEntity> findByBatteryCapacity(String battery);
}
