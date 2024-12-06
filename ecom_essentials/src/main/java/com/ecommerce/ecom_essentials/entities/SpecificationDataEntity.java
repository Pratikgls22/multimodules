package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class SpecificationDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String title;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String data;

    @ManyToOne
    @JoinColumn(name = "specification_id")
    private SpecificationEntity specificationId;

}
