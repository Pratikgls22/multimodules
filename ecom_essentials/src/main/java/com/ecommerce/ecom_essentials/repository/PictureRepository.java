package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity,Long> {
}
