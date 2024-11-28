package com.ecommerce.ecom_essentials.responseDto;

import com.ecommerce.ecom_essentials.entities.BrandEntity;
import lombok.Data;

import java.util.List;

@Data
public class PhoneResponse {
    private int status;
    private String message;
    private List<BrandEntity> data;
}

