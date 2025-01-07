package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ModelDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceName;
    private String deviceImage;
    private String displaySize;
    private String displayResolution;
    private String camera;
    private String video;
    private String ram;
    private String chipset;
    private String battery;
    private String releaseDate;
    private String body;
    private String osType;
    private String storage;

    @Column(columnDefinition = "JSONB")
    private String moreSpecification;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelEntity modelId;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brandId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;
}
