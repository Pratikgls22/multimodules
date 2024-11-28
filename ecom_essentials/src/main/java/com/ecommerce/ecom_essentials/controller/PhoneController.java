package com.ecommerce.ecom_essentials.controller;

import com.ecommerce.ecom_essentials.service.PhoneDataService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/phone")
public class PhoneController {

    private final PhoneDataService phoneDataService;

    @GetMapping("/phoneBrandList")
    public String fetchandSavePhoneData() {
        this.phoneDataService.fetchandSavePhoneData();
        return "Phone data fetched and saved successfully!";
    }

    // use without authentication : permit
    @GetMapping("/deviceList")
    public JsonNode fetchAndSaveDeviceList() {
        return this.phoneDataService.fetchandSaveDeviceList();
    }

    // use without authentication : permit
//    @GetMapping("/deviceDetail")
//    public JsonNode fetchAndSaveDeviceDetail(@RequestParam String key) {
//        return this.phoneDataService.fetchAndSaveDeviceDetail(key);
//    }

}
