package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.DeviceDetailsEntity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceDetailsRepository extends JpaRepository<DeviceDetailsEntity,Long> {
    Optional<DeviceDetailsEntity> findByDeviceName(String deviceName);
}
