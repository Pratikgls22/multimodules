package com.job.females.in.tech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "employer_master")

public class EmployerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "company_phone")
    private String companyPhone;

    @Column(name = "location")
    private String location;

}
