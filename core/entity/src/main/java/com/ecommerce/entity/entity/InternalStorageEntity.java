package com.ecommerce.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "internal_storage", indexes = {
        @Index(name = "index_internal_storage",columnList = "internal_storage",unique = true),
})
public class InternalStorageEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_storage")
    private String internalStorage;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategoryEntity mainCategoryEntity;
}
