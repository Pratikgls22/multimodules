package com.ecommerce.flipkart.repositry;

import com.ecommerce.flipkart.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainCategoryRepository extends JpaRepository <CategoryEntity,Long>{
    Optional<CategoryEntity> findByName(String mainCategoryName);
}
