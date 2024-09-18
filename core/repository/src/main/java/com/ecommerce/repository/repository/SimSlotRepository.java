package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.SimSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimSlotRepository extends JpaRepository<SimSlotEntity,Long> {
    Optional<SimSlotEntity> findBySimSlotType(String simSlot);
}
