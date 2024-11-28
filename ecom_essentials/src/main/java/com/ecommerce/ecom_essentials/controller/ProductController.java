package com.ecommerce.ecom_essentials.controller;

import com.ecommerce.ecom_essentials.enums.Status;
import com.ecommerce.ecom_essentials.requestDto.ProductRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UpdateProductRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ApiResponse;
import com.ecommerce.ecom_essentials.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createProductDraft")
    @PreAuthorize("hasAuthority('Vendor')")
    public ResponseEntity<ApiResponse> createProductDraft(@RequestBody ProductRequestDTO productRequestDTO){
        var response = this.productService.createProductDraft(productRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Successfully Created Product Draft !",response),HttpStatus.OK);
    }

    @GetMapping("/findStatus/status")
    public ResponseEntity<ApiResponse> getDraftsByStatus(@RequestParam Status status){
        var response = this.productService.getDraftsByStatus(status);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Status is :" + status,response),HttpStatus.OK);
    }

    @PostMapping("/{draftId}/approveProductDraft")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> approveDraft(@PathVariable Long draftId){
         this.productService.approveDraft(draftId);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Approved Product !", HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/{productId}/updateProduct")
    @PreAuthorize("hasAuthority('Vendor')")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequestDTO updateProductRequestDTO, @PathVariable Long productId){
        var response = this.productService.updateProduct(productId,updateProductRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Product Updated and Become a productDraft !",response),HttpStatus.OK);
    }

    @PutMapping("/{draftId}/rejectProductDraft")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> rejectProductDraft(@PathVariable Long draftId, @RequestParam(defaultValue = "REJECTED") Status status){
        this.productService.rejectProductDraft(draftId,status);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Your Product Draft is Rejected",HttpStatus.OK),HttpStatus.OK);
    }

}
