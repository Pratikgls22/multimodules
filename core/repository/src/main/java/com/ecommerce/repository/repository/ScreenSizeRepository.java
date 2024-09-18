package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.ScreenSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenSizeRepository extends JpaRepository<ScreenSizeEntity,Long> {
    Optional<ScreenSizeEntity> findByScreenSize(String screenSize);
}
