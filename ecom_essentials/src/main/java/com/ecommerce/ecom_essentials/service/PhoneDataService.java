package com.ecommerce.ecom_essentials.service;


import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface PhoneDataService {
    void fetchandSavePhoneData();

    JsonNode fetchandSaveDeviceList();

//    JsonNode fetchAndSaveDeviceDetail(String key);

}
