package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsBatteryCapacityEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsBatteryCapacityRepository extends ElasticsearchRepository<EsBatteryCapacityEntity,Long> {
    Optional<EsBatteryCapacityEntity> findByBatteryCapacity(String battery);
}
