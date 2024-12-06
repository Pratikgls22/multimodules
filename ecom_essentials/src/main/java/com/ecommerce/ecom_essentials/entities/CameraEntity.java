package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class CameraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mainCamera;
    private String selfieCamera;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brandId;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelEntity modelId;
}
