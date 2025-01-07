package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import com.ecommerce.ecom_essentials.entities.CameraEntity;
import com.ecommerce.ecom_essentials.entities.ModelDetailsEntity;
import com.ecommerce.ecom_essentials.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<CameraEntity,Long> {

//    Optional<CameraEntity> findByDeviceDetailsId(DeviceDetailsEntity deviceDetailsId);

//    @Query(value = "SELECT * FROM camera_entity ce WHERE ce.main_camera ilike CONCAT ('%', :camera, '%')", nativeQuery = true)
//    Optional<CameraEntity> findByMainCamera(@Param("camera") String mainCamera);

    Optional<CameraEntity> findByModelId(ModelEntity modelEntity);

//    @Query(value = "select ce.main_camera, ce.selfie_camera from camera_entity ce ;", nativeQuery = true)
//    List<String> findByCamera();

    @Query(value = """ 
            SELECT * FROM camera_entity ce WHERE 
                     ce.model_id = :modelId
            """ , nativeQuery = true)
    Optional<CameraEntity> findCameraByModelId(@Param("modelId") Long modelId);

    @Query(value = "select ce.main_camera, ce.selfie_camera from camera_entity as ce \n" +
            "join model_entity as me on me.id = ce.model_id \n" +
            "where me.device_name like CONCAT ('%', :model, '%')", nativeQuery = true)
    List<String> findByCameraByModel(@Param("model") String model);
}
