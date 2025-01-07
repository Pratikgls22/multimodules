package com.ecommerce.ecom_essentials.service;

import com.ecommerce.ecom_essentials.entities.ProductDraftEntity;
import com.ecommerce.ecom_essentials.enums.Status;
import com.ecommerce.ecom_essentials.requestDto.ProductRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UpdateProductRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ProductDraftProjection;
import com.ecommerce.ecom_essentials.responseDto.ProductsProjection;

import java.util.List;

public interface ProductService {
    ProductDraftEntity createProductDraft(ProductRequestDTO productRequestDTO);

    void approveDraft(Long draftId);

    ProductDraftEntity updateProduct(Long productId, UpdateProductRequestDTO updateProductRequestDTO);

    List<ProductDraftProjection> getDraftsByStatus(Status status);

    void rejectProductDraft(Long draftId);

    List<ProductsProjection> fetchAllProducts();

    List<ProductDraftProjection> fetchProductDraftsByVendor(Long vendorId);
}
