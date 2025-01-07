package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsSimSlotEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsSimSlotRepository extends ElasticsearchRepository<EsSimSlotEntity,Long> {
    Optional<EsSimSlotEntity> findBySimSlotType(String simSlot);
}
