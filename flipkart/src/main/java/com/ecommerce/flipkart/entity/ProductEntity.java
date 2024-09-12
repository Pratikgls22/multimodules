package com.ecommerce.flipkart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity mainCategoryEntityId;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", referencedColumnName = "id")
    private CategoryEntity subCategoryEntityId;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private ModelEntity modelEntityId;

    @ManyToOne
    @JoinColumn(name = "Color_id", referencedColumnName = "id")
    private ColorEntity colorEntityId;

    @ManyToOne
    @JoinColumn(name = "Ram_id", referencedColumnName = "id")
    private RamEntity ramEntityId;

    @ManyToOne
    @JoinColumn(name = "Internal_Storage_id", referencedColumnName = "id")
    private InternalStorageEntity storageId;

    @ManyToOne
    @JoinColumn(name = "Price_id", referencedColumnName = "id")
    private PriceEntity priceEntityId;
}
