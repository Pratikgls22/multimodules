package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ModelDetailsEntity;
import com.ecommerce.ecom_essentials.responseDto.ModelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelDetailsRepository extends JpaRepository<ModelDetailsEntity,Long> {
    Optional<ModelDetailsEntity> findByDeviceName(String deviceName);


    @Query(value = "select ce.category, be.brand_name as brand, de.device_name as model, dde.battery, dde.body, dde.camera, dde.chipset, dde.device_image, dde.display_resolution,dde.display_size,\n" +
            "dde.os_type,dde.ram, dde.release_date, dde.storage, dde.video, dde.more_specification  from model_details_entity as dde\n" +
            "join brand_entity as be on be.id = dde.brand_id \n" +
            "join model_entity as de on de.id = dde.model_id \n" +
            "join category_entity as ce on ce.id =dde.category_id \n"+
            "where be.brand_name like concat('%',:brandName,'%') ;",nativeQuery = true)
    List<ModelProjection> findByBrandId_BrandName(@Param("brandName") String brandIdBrandName);
}
