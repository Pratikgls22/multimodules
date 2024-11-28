package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class RelatedDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceName;
    private String deviceImage;
    private String key;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceDetailsEntity deviceDetailsId;
}
