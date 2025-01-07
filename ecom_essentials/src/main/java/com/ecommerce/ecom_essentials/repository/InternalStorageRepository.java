package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import com.ecommerce.ecom_essentials.entities.InternalStorageEntity;
import com.ecommerce.ecom_essentials.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalStorageRepository extends JpaRepository<InternalStorageEntity, Long> {
    Optional<InternalStorageEntity> findByInternalStorage(String specificMemory);

    @Query(value = "select ise.internal_storage  from internal_storage_entity ise ;", nativeQuery = true)
    List<String> findByStorage();

//    Optional<InternalStorageEntity> findByInternalStorageAndBrandIdAndModelId(String text, BrandEntity brand, ModelEntity modelEntity);
}
