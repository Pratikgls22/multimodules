package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceName;
    private String deviceType;
    private String deviceImage;
    private String key;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brandId;
}
