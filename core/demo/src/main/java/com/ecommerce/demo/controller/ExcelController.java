package com.ecommerce.demo.controller;

import com.ecommerce.demo.service.ExcelService;
import com.ecommerce.entity.responseDto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/excel")
@CrossOrigin("http://localhost:9093")
@RequiredArgsConstructor
@Slf4j
public class ExcelController {

    private final ExcelService excelService;

    @PostMapping("/saveExcel")
    public ResponseEntity<ApiResponse> uploadExcelFile(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        this.excelService.readFileAndSave(multipartFile);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "File Saved!", new HashMap<>()), HttpStatus.OK);
    }
}
