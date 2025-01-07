package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsColorEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EsColorRepository extends ElasticsearchRepository<EsColorEntity,Long> {
    Optional<EsColorEntity> findByColor(String color);
    // In EsColorRepository
//    List<EsColorEntity> saveAll(List<EsColorEntity> entities);
}
