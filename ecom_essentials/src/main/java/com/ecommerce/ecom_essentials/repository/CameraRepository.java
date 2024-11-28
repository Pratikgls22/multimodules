package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.CameraEntity;
import com.ecommerce.ecom_essentials.entities.DeviceDetailsEntity;
import com.ecommerce.ecom_essentials.entities.SpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<CameraEntity,Long> {

    Optional<CameraEntity> findByDeviceDetailsId(DeviceDetailsEntity deviceDetailsId);

    @Query(value = "SELECT * FROM camera_entity ce WHERE ce.main_camera LIKE :mainCamera% LIMIT 1", nativeQuery = true)
    Optional<CameraEntity> findByMainCamera(@Param("mainCamera") String mainCamera);

    Optional<CameraEntity> findBySelfieCamera(String selfieCamera);
}
