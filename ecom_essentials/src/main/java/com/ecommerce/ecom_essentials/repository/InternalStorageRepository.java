package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.InternalStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalStorageRepository extends JpaRepository<InternalStorageEntity, Long> {
    Optional<InternalStorageEntity> findByInternalStorage(String specificMemory);
}
