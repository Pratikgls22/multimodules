package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.SpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface SpecificationRepository extends JpaRepository<SpecificationEntity,Long> {
//    Optional<SpecificationEntity> findByTitle(String mainCamera);
}
