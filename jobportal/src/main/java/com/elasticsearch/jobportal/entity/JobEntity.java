package com.elasticsearch.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "job_tbl")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobSalary;
    private String jobExperienceRequired;
    private String keySkills;
    private String roleCategory;
    private String functionalArea;
    private String industry;
    private String jobTitle;

    public JobEntity(String jobSalary, String jobExperienceRequired, String keySkills, String roleCategory, String functionalArea, String industry, String jobTitle) {
        this.jobSalary = jobSalary;
        this.jobExperienceRequired = jobExperienceRequired;
        this.keySkills = keySkills;
        this.roleCategory = roleCategory;
        this.functionalArea = functionalArea;
        this.industry = industry;
        this.jobTitle = jobTitle;
    }
}
