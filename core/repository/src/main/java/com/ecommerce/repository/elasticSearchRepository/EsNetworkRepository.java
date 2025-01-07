package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsNetworkEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsNetworkRepository extends ElasticsearchRepository<EsNetworkEntity,Long> {
    Optional<EsNetworkEntity> findByNetworkType(String network);
}
