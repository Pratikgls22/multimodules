package com.ecommerce.flipkart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String mainCategoryName;
    private String subCategoryName;
    private String modelName;
    private String colorName;
    private String ramSize;
    private String storageSize;
    private Integer price;
}
