package com.ecommerce.ecom_essentials.controller;

import com.ecommerce.ecom_essentials.responseDto.ApiResponse;
import com.ecommerce.ecom_essentials.service.PhoneDataService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/phone")
public class PhoneController {

    private final PhoneDataService phoneDataService;

    // use without authentication : permit
    @GetMapping("/deviceList")
    public JsonNode fetchAndSaveDeviceList() {
        return this.phoneDataService.fetchandSaveDeviceList();
    }

    @GetMapping("/fetchBrands")
    public ResponseEntity<ApiResponse> fetchBrandList(){
        var response = this.phoneDataService.fetchBrandList();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Brands!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchDetailsOfModel/{brand}")
    public ResponseEntity<ApiResponse> fetchDeatilsOfModel(@PathVariable(value = "brand") String brand){
        var response = this.phoneDataService.fetchDetailsOfModel(brand);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Details of Brand : "+ brand,response),HttpStatus.OK);
    }

    @GetMapping("/fetchModelByBrand/{brand}")
    public ResponseEntity<ApiResponse> fetchModelByBrand(@PathVariable(value = "brand") String brand){
        var response = this.phoneDataService.fetchModelByBrand(brand);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Model of Brand:"+brand,response), HttpStatus.OK);
    }

    // use without authentication : permit
//    @GetMapping("/deviceDetail")
//    public JsonNode fetchAndSaveDeviceDetail(@RequestParam String key) {
//        return this.phoneDataService.fetchAndSaveDeviceDetail(key);
//    }

}
