package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import com.ecommerce.ecom_essentials.entities.ModelEntity;
import com.ecommerce.ecom_essentials.entities.RamStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RamRepository extends JpaRepository<RamStorageEntity, Long> {
    Optional<RamStorageEntity> findByRamStorage(String ram);

    @Query(value = "select rse.ram_storage  from ram_storage_entity rse ;", nativeQuery = true)
    List<String> findByRam();

//    Optional<RamStorageEntity> findByRamStorageAndBrandIdAndModelId(String ram, BrandEntity brand, ModelEntity modelEntity);
}
