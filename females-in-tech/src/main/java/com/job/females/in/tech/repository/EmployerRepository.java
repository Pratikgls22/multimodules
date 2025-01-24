package com.job.females.in.tech.repository;

import com.job.females.in.tech.entity.EmployerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerEntity, Long> {

    Optional<EmployerEntity> findByCompanyEmailIgnoreCaseAndIsDeleteFalse(String email);
}
