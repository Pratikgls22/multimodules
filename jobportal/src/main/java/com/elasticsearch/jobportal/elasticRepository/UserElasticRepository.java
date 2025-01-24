package com.elasticsearch.jobportal.elasticRepository;

import com.elasticsearch.jobportal.elasticEntity.UserElasticEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElasticRepository extends ElasticsearchRepository<UserElasticEntity, Long> {
}
