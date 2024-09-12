package com.ecommerce.flipkart.service;

import com.ecommerce.flipkart.dto.ProductRequestDto;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void adduser(ProductRequestDto productRequestDto);

    void saveCustomersToDatabase(MultipartFile file);
}
