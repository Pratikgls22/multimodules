package com.ecommerce.ecom_essentials.service;

import com.ecommerce.ecom_essentials.entities.ProductDraftEntity;
import com.ecommerce.ecom_essentials.enums.Status;
import com.ecommerce.ecom_essentials.requestDto.ProductRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UpdateProductRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ProductProjection;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductService {
    ProductDraftEntity createProductDraft(ProductRequestDTO productRequestDTO);

    void approveDraft(Long draftId);

    ProductDraftEntity updateProduct(Long productId, UpdateProductRequestDTO updateProductRequestDTO);

    List<ProductProjection> getDraftsByStatus(Status status);

    void rejectProductDraft(Long draftId, Status status);
}
