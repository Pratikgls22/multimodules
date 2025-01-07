package com.ecommerce.ecom_essentials.controller;

import com.ecommerce.ecom_essentials.responseDto.ApiResponse;
import com.ecommerce.ecom_essentials.service.PhoneDataService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.Pattern;
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

    @GetMapping("/fetchBattery")
    public ResponseEntity<ApiResponse> fetchBatteryList(){
        var response = this.phoneDataService.fetchBatteryList();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Batteries!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchCamera/{model}")
    public ResponseEntity<ApiResponse> fetchCameraList(@PathVariable(value = "model") String model){
        var response = this.phoneDataService.fetchCameraList(model);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Cameras!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchImages/{model}")
    public ResponseEntity<ApiResponse> fetchImageList(@PathVariable(value = "model") String model){
        var response = this.phoneDataService.fetchImageList(model);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Cameras!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchInternalStorage")
    public ResponseEntity<ApiResponse> fetchInternalStorageList(){
        var response = this.phoneDataService.fetchInternalStorageList();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Cameras!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchColor")
    public ResponseEntity<ApiResponse> fetchColorList(){
        var response = this.phoneDataService.fetchColorList();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Brands!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchOperatingSystem")
    public ResponseEntity<ApiResponse> fetchOperatingSystemList(){
        var response = this.phoneDataService.fetchOperatingSystemList();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Brands!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchRam")
    public ResponseEntity<ApiResponse> fetchRamList(){
        var response = this.phoneDataService.fetchRamList();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"List of All Brands!",response),HttpStatus.OK);
    }

    @GetMapping("/fetchModel/{brand}")
    public ResponseEntity<ApiResponse> fetchModelList(@PathVariable(value = "brand")String brand) {
        var response = this.phoneDataService.fetchModelList(brand);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "List of All Models", response), HttpStatus.OK);
    }

    @GetMapping("/fetchDetailsOfModel/{brand}")
    public ResponseEntity<ApiResponse> fetchDeatilsOfModel(@PathVariable(value = "brand") String brand){
        var response = this.phoneDataService.fetchDetailsOfModel(brand);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Details of Brand : "+ brand,response),HttpStatus.OK);
    }



    // use without authentication : permit
//    @GetMapping("/deviceDetail")
//    public JsonNode fetchAndSaveDeviceDetail(@RequestParam String key) {
//        return this.phoneDataService.fetchAndSaveDeviceDetail(key);
//    }

}
