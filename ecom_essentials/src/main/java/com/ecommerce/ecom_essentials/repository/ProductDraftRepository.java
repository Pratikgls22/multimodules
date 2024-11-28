package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.ProductDraftEntity;
import com.ecommerce.ecom_essentials.enums.Status;
import com.ecommerce.ecom_essentials.responseDto.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDraftRepository extends JpaRepository<ProductDraftEntity, Long> {

    @Query(value = "select pde.id ,pde.brand , pde.main_camera , pde.color ,pde.internal_storage ,pde.model_name ,pde.operating_system ,pde.price ,pde.ram_storage ,pde.status ,pde.battery, pde.user_id as userId \n" +
            "            from product_draft_entity as pde\n" +
            "          where pde.status = :status;",nativeQuery = true)
    List<ProductProjection> findByStatus(@Param("status") String status);

    Optional<ProductDraftEntity> findByModelName(String modelName);
}
