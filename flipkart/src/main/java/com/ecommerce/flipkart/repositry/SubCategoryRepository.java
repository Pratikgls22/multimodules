package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity,Long> {
    Optional<SubCategoryEntity> findByName(String subCategoryName);
}
