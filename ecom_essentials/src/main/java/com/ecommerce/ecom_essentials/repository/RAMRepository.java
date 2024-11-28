package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.RAMStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RAMRepository extends JpaRepository<RAMStorageEntity , Long> {
    Optional<RAMStorageEntity> findByRAMStorage(String specificMemory);
}
