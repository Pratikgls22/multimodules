package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.ProcessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessorRepository extends JpaRepository<ProcessorEntity,Long> {
    Optional<ProcessorEntity> findByProcessorName(String processor);
}
