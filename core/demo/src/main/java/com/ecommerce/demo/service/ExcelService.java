package com.ecommerce.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {
    void readFileAndSave(MultipartFile multipartFile) throws IOException;
}
