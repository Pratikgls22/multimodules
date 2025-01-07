package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

//    Optional<ImageEntity> findByUrlAndBrandIdAndModelId(String text, BrandEntity brand, ModelEntity modelEntity);

    Optional<ImageEntity> findByUrl(String text);

    @Query(value = "select ie.url from image_entity as ie \n" +
            "join model_entity as me on me.id = ie.model_id \n" +
            "where me.device_name like concat('%',:model,'%')", nativeQuery = true)
    List<String> findByImageByModel(@Param("model") String model);

    @Query(value = """
            select * from image_entity ie where ie.model_id = :modelId
            """, nativeQuery = true)
    Optional<ImageEntity> findImageByModelId(@Param("modelId") Long modelId);
}
