package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeviceDetailsEntity {
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

    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceEntity deviceEntityId;
}
