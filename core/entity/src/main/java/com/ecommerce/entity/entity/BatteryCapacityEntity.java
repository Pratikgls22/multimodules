package com.ecommerce.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "battery_capacity", indexes = {
        @Index(name = "index_battery_capacity",columnList = "battery_capacity",unique = true),
})
public class BatteryCapacityEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "battery_capacity")
    private String batteryCapacity;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategoryEntity mainCategoryEntity;
}
