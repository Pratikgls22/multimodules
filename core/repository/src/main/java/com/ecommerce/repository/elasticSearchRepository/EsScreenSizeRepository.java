package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsScreenSizeEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsScreenSizeRepository extends ElasticsearchRepository<EsScreenSizeEntity,Long> {
    Optional<EsScreenSizeEntity> findByScreenSize(String screenSize);
}
