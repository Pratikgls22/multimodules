package com.elasticsearch.jobportal.config;

import com.elasticsearch.jobportal.elasticEntity.JobElasticEntity;
import com.elasticsearch.jobportal.entity.JobEntity;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class JobMasterProcessor implements ItemProcessor<JobEntity, List<Object>> {
    @Override
    public List<Object> process(JobEntity jobEntity) throws Exception {
        // Create a list to hold both entities
        List<Object> result = new ArrayList<>();

        // Process JobEntity for the database writer
        jobEntity.setId(null);  // Clear the ID for the database write
        result.add(jobEntity);   // Add JobEntity to the list for the database writer

        // Convert JobEntity to JobElasticEntity for Elasticsearch
        JobElasticEntity jobElasticEntity = new JobElasticEntity();
        jobElasticEntity.setId(jobEntity.getId());
        jobElasticEntity.setJobSalary(jobEntity.getJobSalary());
        jobElasticEntity.setJobExperienceRequired(jobEntity.getJobExperienceRequired());
        jobElasticEntity.setKeySkills(jobEntity.getKeySkills());
        jobElasticEntity.setRoleCategory(jobEntity.getRoleCategory());
        jobElasticEntity.setFunctionalArea(jobEntity.getFunctionalArea());
        jobElasticEntity.setIndustry(jobEntity.getIndustry());
        jobElasticEntity.setJobTitle(jobEntity.getJobTitle());

        result.add(jobElasticEntity);  // Add JobElasticEntity to the list for the Elasticsearch writer

        return result;
    }
}


//public class JobMasterProcessor implements ItemProcessor<JobEntity, JobEntity>{
//
//    @Override
//    public JobEntity process(JobEntity jobEntity) {
//        jobEntity.setId(null);
//        return jobEntity;
//    }
//}