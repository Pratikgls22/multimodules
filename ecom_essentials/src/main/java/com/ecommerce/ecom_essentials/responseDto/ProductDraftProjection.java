package com.ecommerce.ecom_essentials.responseDto;

import com.ecommerce.ecom_essentials.enums.Status;

public interface ProductDraftProjection {
     Long getId();
     String getBrand();
     String getModelName();
     String getColor();
     String getRamStorage();
     String getInternalStorage();
     String getCamera();
     String getBattery();
     String getImage();
     String getOperatingSystem();
     String getPrice();
     Status getStatus();
     Long getUserId();
}
