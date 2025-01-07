package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsColorEntity;
import com.ecommerce.entity.elasticSerachEntity.EsInternalStorageEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EsInternalStorageRepository extends ElasticsearchRepository<EsInternalStorageEntity,Long> {
    Optional<EsInternalStorageEntity> findByInternalStorage(String internalStorage);
//    List<EsInternalStorageEntity> saveAll(List<EsInternalStorageEntity> entities);
}
