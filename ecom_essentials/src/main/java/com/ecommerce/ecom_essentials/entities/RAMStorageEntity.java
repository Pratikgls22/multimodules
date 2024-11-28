package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class RAMStorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String RAMStorage;

    @ManyToOne
    @JoinColumn(name = "device_details_id")
    private DeviceDetailsEntity deviceDetailsId;
}
