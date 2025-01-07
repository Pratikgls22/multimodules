package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsModelEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsModelRepository extends ElasticsearchRepository<EsModelEntity, Long> {
    Optional<EsModelEntity> findByModalName(String model);
}
