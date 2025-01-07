package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SpecificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String title;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brandId;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelEntity modelId;
}
