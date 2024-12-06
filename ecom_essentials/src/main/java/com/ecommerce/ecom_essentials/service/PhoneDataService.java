package com.ecommerce.ecom_essentials.service;


import com.ecommerce.ecom_essentials.entities.BrandEntity;
import com.ecommerce.ecom_essentials.entities.ModelEntity;
import com.ecommerce.ecom_essentials.responseDto.ModelProjection;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface PhoneDataService {
//    void fetchandSavePhoneData();

    JsonNode fetchandSaveDeviceList();

    List<ModelProjection> fetchDetailsOfModel(String model);

    List<String> fetchBrandList();

    List<String> fetchModelByBrand(String brand);

//    JsonNode fetchAndSaveDeviceDetail(String key);

}
