package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsBrandEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsBrandRepository extends ElasticsearchRepository<EsBrandEntity,Long> {
    Optional<EsBrandEntity> findByBrandName(String brand);
}
