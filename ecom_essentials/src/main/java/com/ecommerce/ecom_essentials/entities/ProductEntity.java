package com.ecommerce.ecom_essentials.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brandId;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelEntity modelId;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorEntity colorId;

    @ManyToOne
    @JoinColumn(name = "ram_storage_id")
    private RamStorageEntity ramStorageId;

    @ManyToOne
    @JoinColumn(name = "internal_storage_id")
    private InternalStorageEntity internalStorageId;

    @ManyToOne
    @JoinColumn(name = "camera_id")
    private CameraEntity cameraId;

    @ManyToOne
    @JoinColumn(name = "battery_id")
    private BatteryEntity batteryId;

    @ManyToOne
    @JoinColumn(name = "operating_system_id")
    private OperatingSystemEntity operatingSystemId;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity imageId;

    private String price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userId;

}
