package com.elasticsearch.jobportal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class JobEntity {
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

    public JobEntity(String jobSalary, String jobExperienceRequired, String keySkills, String roleCategory, String functionalArea, String industryJob, String title) {
    }
}
