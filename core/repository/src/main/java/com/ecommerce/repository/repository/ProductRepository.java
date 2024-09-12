package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.*;
import com.ecommerce.entity.requestDto.ExcelRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
//    Optional<ProductEntity> findByCategoryEntityAndSubCategoryEntityAndModelEntityAndColorEntityAndRamEntityAndInternalStorageEntityAndPriceEntity
//            (CategoryEntity categoryEntity, CategoryEntity subCategoryEntity, ModelEntity modelEntity,ColorEntity colorEntity, RamEntity ramEntity,InternalStorageEntity internalStorageEntity, PriceEntity priceEntity);

//    @Query("SELECT p FROM ProductEntity p WHERE p.categoryEntity = :#{#excelRequestDto.categoryEntity} " +
//            "AND p.subCategoryEntity = :#{#excelRequestDto.subCategoryEntity} " +
//            "AND p.modelEntity = :#{#excelRequestDto.modelEntity} " +
//            "AND p.colorEntity = :#{#excelRequestDto.colorEntity} " +
//            "AND p.ramEntity = :#{#excelRequestDto.ramEntity} " +
//            "AND p.internalStorageEntity = :#{#excelRequestDto.internalStorageEntity} " +
//            "AND p.priceEntity = :#{#excelRequestDto.priceEntity}")
//    Optional<ProductEntity> findByAttributes(ExcelRequestDto excelRequestDto);

     // this is java 14 feature text blocks
    @Query("""
            SELECT p FROM ProductEntity p WHERE p.categoryEntity = :#{#excelRequestDto.categoryEntity} 
                      AND p.subCategoryEntity = :#{#excelRequestDto.subCategoryEntity}  
                       AND p.modelEntity = :#{#excelRequestDto.modelEntity} 
                       AND p.colorEntity = :#{#excelRequestDto.colorEntity}
                       AND p.ramEntity = :#{#excelRequestDto.ramEntity}
                       AND p.internalStorageEntity = :#{#excelRequestDto.internalStorageEntity}
                   AND p.priceEntity = :#{#excelRequestDto.priceEntity}
           """)
    Optional<ProductEntity> findByAttributes(ExcelRequestDto excelRequestDto);

}




