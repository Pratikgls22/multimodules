package com.ecommerce.ecom_essentials.responseDto;

import com.ecommerce.ecom_essentials.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductResponseDTO {

    private Long id;
    private String brand;
    private String modelName;
    private String color;
    private String ramStorage;
    private String internalStorage;
    private String camera;
    private String battery;
    private String operatingSystem;
    private String price;
    private Status status;
    private Long productId;
    private Long userId;

}
