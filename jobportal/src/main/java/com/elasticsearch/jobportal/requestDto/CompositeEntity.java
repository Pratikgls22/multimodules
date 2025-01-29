package com.elasticsearch.jobportal.requestDto;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import com.elasticsearch.jobportal.entity.JobEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompositeEntity {

    private JobEntity jobEntity;
    private JobElasticEntity jobElasticEntity;
}
