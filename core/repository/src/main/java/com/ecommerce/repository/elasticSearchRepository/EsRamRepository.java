package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsRamEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EsRamRepository extends ElasticsearchRepository<EsRamEntity,Long> {
    Optional<EsRamEntity>findByRam(String ram);
//    List<EsRamEntity> saveAll(List<EsRamEntity> entities);
}
