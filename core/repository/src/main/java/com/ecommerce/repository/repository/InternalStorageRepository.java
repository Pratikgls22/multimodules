package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.InternalStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalStorageRepository extends JpaRepository<InternalStorageEntity,Long> {
    Optional<InternalStorageEntity> findByInternalStorage(String internalStorage);
}
