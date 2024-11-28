package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.SpecificationDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationDataRepository extends JpaRepository<SpecificationDataEntity,Long> {
//    List<SpecificationDataEntity> findBySpecificationId(Long specificationId);

//    List<SpecificationDataEntity> findByTitleIn(List<String> title);
}
