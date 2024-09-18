package com.ecommerce.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "network", indexes = {
        @Index(name = "index_network",columnList = "network_type",unique = true),
})
public class NetworkEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "network_type")
    private String networkType;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategoryEntity mainCategoryEntity;
}
