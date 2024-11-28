package com.ecommerce.ecom_essentials.requestDto;

import com.ecommerce.ecom_essentials.enums.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductRequestDTO {

    private String brand;
    private String modelName;
    private String color;
    private String ramStorage;
    private String internalStorage;
    private String mainCamera;
    private String battery;
    private String operatingSystem;
    private String price;
    private Status status; // Default to PENDING
    private Long productId;
}
