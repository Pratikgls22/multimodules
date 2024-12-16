package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity,Long> {
    Optional<DeviceEntity> findByKey(String key);
}
