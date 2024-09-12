package com.ecommerce.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalStorageEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Internal_Storage")
    private String internalStorage;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private CategoryEntity categoryEntity;
}
