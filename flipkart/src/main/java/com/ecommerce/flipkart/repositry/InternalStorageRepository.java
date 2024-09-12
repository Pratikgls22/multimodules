package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.InternalStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalStorageRepository extends JpaRepository<InternalStorageEntity,Long> {
    Optional<InternalStorageEntity> findBySize(String storageSize);
}
