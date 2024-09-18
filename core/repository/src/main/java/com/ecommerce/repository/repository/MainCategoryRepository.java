package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.MainCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategoryEntity,Long> {
    Optional<MainCategoryEntity> findByCategoryName(String category);
}
