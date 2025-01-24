package com.elasticsearch.jobportal.elasticEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "job_elastic_master")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class JobElasticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobSalary;
    private String jobExperienceRequired;
    private String keySkills;
    private String roleCategory;
    private String functionalArea;
    private String industryJob;
    private String jobTitle;


}
