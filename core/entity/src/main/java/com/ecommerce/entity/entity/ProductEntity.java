package com.ecommerce.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Category_Id")
    private CategoryEntity categoryEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Sub_Category_Id")
    private CategoryEntity subCategoryEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Model_Id")
    private ModelEntity modelEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Color_Id")
    private ColorEntity colorEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Ram_Id")
    private RamEntity ramEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Internal_Storage_Id")
    private InternalStorageEntity internalStorageEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Price_Id")
    private PriceEntity priceEntity;
}
