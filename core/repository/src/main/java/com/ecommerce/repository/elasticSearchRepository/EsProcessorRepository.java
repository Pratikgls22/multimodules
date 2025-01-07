package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsProcessorEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EsProcessorRepository extends ElasticsearchRepository<EsProcessorEntity,Long> {
    Optional<EsProcessorEntity> findByProcessorName(String processor);
}
