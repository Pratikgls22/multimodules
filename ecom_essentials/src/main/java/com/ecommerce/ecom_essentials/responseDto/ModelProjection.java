package com.ecommerce.ecom_essentials.responseDto;



public interface ModelProjection {
    String getDeviceImage();
    String getDisplaySize();
    String getDisplayResolution();
    String getCamera();
    String getVideo();
    String getRam();
    String getChipset();
    String getBattery();
    String getReleaseDate();
    String getBody();
    String getOsType();
    String getStorage();
    String getMoreSpecification(); // JSONB as a String
    String getModel();
    String getBrand();
    String getCategory();
}
