package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ProductEntity;
import com.ecommerce.ecom_essentials.responseDto.ProductDraftProjection;
import com.ecommerce.ecom_essentials.responseDto.ProductsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = """
            select pe.id ,pe.price , be.brand_name as brand, me.device_name as modelName, ce.main_camera as mainCamera, ce.selfie_camera as selfieCamera, rse.ram_storage as ramStorage,be2.battery_capacity as battery,
                                            ise.internal_storage as internalStorage, ce2.color as color, ie.url as image, ose.operating_system as operatingSystem, mde.more_specification
                                            from product_entity pe
                                            join model_details_entity mde on mde.model_id = pe.model_id
                                            join brand_entity be ON be.id = pe.brand_id\s
                                            join model_entity me on me.id = pe.model_id\s
                                            join battery_entity be2 ON be2.id = pe.battery_id\s
                                            join camera_entity ce on ce.id = pe.camera_id\s
                                            join color_entity ce2 on ce2.id = pe.color_id\s
                                            join image_entity ie on ie.id = pe.image_id\s
                                            join ram_storage_entity rse on rse.id = pe.ram_storage_id\s
                                            join internal_storage_entity ise on ise.id = pe.internal_storage_id\s
                                            join operating_system_entity ose on ose.id = pe.operating_system_id\s
                                            join user_master um on um.id = pe.user_id\s
            
            """, nativeQuery = true)
    List<ProductsProjection> findAllProducts();
}
