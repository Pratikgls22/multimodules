package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.RelatedDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatedDeviceRepository extends JpaRepository<RelatedDeviceEntity,Long> {
}
