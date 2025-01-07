package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsMainCategoryEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsMainCategoryRepository extends ElasticsearchRepository<EsMainCategoryEntity,Long> {
    Optional<EsMainCategoryEntity> findByCategoryName(String category);
}
