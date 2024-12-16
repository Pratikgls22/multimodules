package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.RamStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RAMRepository extends JpaRepository<RamStorageEntity, Long> {
    Optional<RamStorageEntity> findByRAMStorage(String specificMemory);
}
