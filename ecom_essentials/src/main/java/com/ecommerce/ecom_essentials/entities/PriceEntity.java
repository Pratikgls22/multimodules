package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String storageOption;
//    private String shopImage;
    private String price;
//    private String buyUrl;


    @ManyToOne
    @JoinColumn(name = "specification_data_id")
    private SpecificationDataEntity specificationDataEntity;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceDetailsEntity deviceDetailsId;
}
