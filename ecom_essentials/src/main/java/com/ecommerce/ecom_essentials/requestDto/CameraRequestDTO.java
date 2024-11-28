package com.ecommerce.ecom_essentials.requestDto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CameraRequestDTO {
    private String backCameraSpecifications;
    private String selfieCameraSpecifications;
}

