package com.elasticsearch.jobportal.elasticRepository;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobElasticRepository extends ElasticsearchRepository<JobElasticEntity,Long> {

    List<JobElasticEntity> findByFunctionalArea(String functionalArea);
}
