package com.ecommerce.ecom_essentials.service;


import com.ecommerce.ecom_essentials.responseDto.ModelProjection;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface PhoneDataService {
//    void fetchandSavePhoneData();

    JsonNode fetchandSaveDeviceList();

    List<ModelProjection> fetchDetailsOfModel(String model);

    List<String> fetchBrandList();

    List<String> fetchBatteryList();

    List<String> fetchColorList();

    List<String> fetchModelList(String brand);

    List<String> fetchCameraList(String model);

    List<String> fetchImageList(String model);

    List<String> fetchInternalStorageList();

    List<String> fetchOperatingSystemList();

    List<String> fetchRamList();

//    JsonNode fetchAndSaveDeviceDetail(String key);

}
