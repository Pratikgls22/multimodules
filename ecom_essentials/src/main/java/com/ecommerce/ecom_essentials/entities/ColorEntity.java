package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceDetailsEntity deviceDetailsId;
}
