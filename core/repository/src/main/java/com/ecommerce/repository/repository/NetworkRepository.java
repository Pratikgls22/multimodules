package com.ecommerce.repository.repository;

import com.ecommerce.entity.entity.NetworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NetworkRepository extends JpaRepository<NetworkEntity,Long> {
    Optional<NetworkEntity> findByNetworkType(String network);
}
