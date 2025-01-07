package com.ecommerce.ecom_essentials.service.impl;

import com.ecommerce.ecom_essentials.entities.*;
import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import com.ecommerce.ecom_essentials.enums.Status;
import com.ecommerce.ecom_essentials.exception.CustomException;
import com.ecommerce.ecom_essentials.repository.*;
import com.ecommerce.ecom_essentials.requestDto.ProductRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UpdateProductRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ProductDraftProjection;
import com.ecommerce.ecom_essentials.responseDto.ProductsProjection;
import com.ecommerce.ecom_essentials.service.ProductService;
import com.ecommerce.ecom_essentials.utility.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductDraftRepository productDraftRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final Utilities utilities;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final CameraRepository cameraRepository;
    private final RamRepository ramRepository;
    private final InternalStorageRepository internalStorageRepository;
    private final BatteryRepository batteryRepository;
    private final OperatingSystemRepository operatingSystemRepository;
    private final ModelRepository modelRepository;
    private final ImageRepository imageRepository;

    @Override
    public ProductDraftEntity createProductDraft(ProductRequestDTO productRequestDTO) {

        ProductDraftEntity productModelName = this.productDraftRepository.findByModelName(productRequestDTO.getModelName())
                .orElseGet(() -> {

                    UserEntity currentUser = utilities.currentUser();

                    ProductDraftEntity productDraft = ProductDraftEntity.builder()
                            .brand(productRequestDTO.getBrand())
                            .modelName(productRequestDTO.getModelName())
                            .image(productRequestDTO.getImage())
                            .color(productRequestDTO.getColor())
                            .ramStorage(productRequestDTO.getRamStorage())
                            .internalStorage(productRequestDTO.getInternalStorage())
                            .camera(productRequestDTO.getCamera())
                            .battery(productRequestDTO.getBattery())
                            .operatingSystem(productRequestDTO.getOperatingSystem())
                            .price(productRequestDTO.getPrice())
                            .status(productRequestDTO.getStatus() == null ? null : Status.PENDING)   // Default to PENDING if null
                            .productId(null)
                            .build();
                    productDraft.setCreatedBy(currentUser);
                    productDraft.setUpdatedBy(currentUser);
                    productDraft.setUserId(currentUser);
                    return productDraftRepository.save(productDraft);
                });
        return productModelName;
    }

    @Override
    public List<ProductDraftProjection> getDraftsByStatus(Status status) {
        return productDraftRepository.findByStatus(String.valueOf(status));
    }

    @Override
    public void rejectProductDraft(Long draftId) {
        System.out.println("draftId = " + draftId);
        var productDraftOptional = this.productDraftRepository.findById(draftId);
        System.out.println("productDraftOptional = " + productDraftOptional);
        if (productDraftOptional.isEmpty()) {
            log.info("ProductDraftId is Empty :: {}", productDraftOptional);
            throw new CustomException(ExceptionEnum.PRODUCT_DRAFT_ID_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        ProductDraftEntity productDraftEntity = productDraftOptional.get();

        // Update status only if it's not already REJECTED
        if (!Status.REJECTED.equals(productDraftEntity.getStatus())) {
            productDraftEntity.setStatus(Status.REJECTED);
            // Save updated entity
            this.productDraftRepository.save(productDraftEntity);
        }
    }

    @Override
    public List<ProductsProjection> fetchAllProducts() {
        return this.productRepository.findAllProducts();
    }

    @Override
    public List<ProductDraftProjection> fetchProductDraftsByVendor(Long vendorId) {
        var vendor = this.productDraftRepository.findByUserId(vendorId);
        if (vendor.isEmpty()){
             throw new CustomException(ExceptionEnum.USER_ID_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        return vendor;
    }

    @Override
    public void approveDraft(Long draftId) {

        UserEntity currentUser = utilities.currentUser();

        // Find Product Draft Id:
        var productDraft = this.productDraftRepository.findById(draftId);
        if (productDraft.isEmpty()) {
            log.info("ProductDraft is Empty :: {}", productDraft);
            throw new CustomException(ExceptionEnum.PRODUCT_DRAFT_ID_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        ProductDraftEntity productDraftEntity = productDraft.get();

        if (productDraftEntity.getStatus() != Status.PENDING) {
            throw new CustomException(ExceptionEnum.DRAFT_NOT_PENDING.getMessage(), HttpStatus.CONFLICT);
        }

        // For Create Product:
        createProduct(productDraftEntity, currentUser);

        // Change Status Pending to Approve :
        productDraftEntity.setStatus(Status.APPROVED);
        this.productDraftRepository.save(productDraftEntity);
    }

    @Override
    public ProductDraftEntity updateProduct(Long productId, UpdateProductRequestDTO updateProductRequestDTO) {

        UserEntity currentUser = utilities.currentUser();

        ProductEntity product = this.productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PRODUCT_ID_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        ProductDraftEntity productDraft = ProductDraftEntity.builder()
                .productId(product)
                .brand(updateProductRequestDTO.getBrand())
                .modelName(updateProductRequestDTO.getModelName())
                .color(updateProductRequestDTO.getColor())
                .ramStorage(updateProductRequestDTO.getRamStorage())
                .internalStorage(updateProductRequestDTO.getInternalStorage())
                .camera(updateProductRequestDTO.getMainCamera())
                .battery(updateProductRequestDTO.getBattery())
                .operatingSystem(updateProductRequestDTO.getOperatingSystem())
                .price(updateProductRequestDTO.getPrice())
                .status(updateProductRequestDTO.getStatus() == null ? null : Status.PENDING)   // Default to PENDING if null
                .userId(currentUser)
                .build();
        productDraft.setUpdatedBy(currentUser);
        productDraft.setCreatedBy(currentUser);
        productDraftRepository.save(productDraft);
        return productDraft;
    }

    // For Create Product :
    private void createProduct(ProductDraftEntity productDraftEntity, UserEntity currentUser) {
        // Split ProductEntity Cameras Data:
        String cameras = productDraftEntity.getCamera();
        String[] cameraParts = cameras.split(",", 2);
        String mainCamera = cameraParts[0].trim();
        System.out.println("mainCamera ==== " + mainCamera);
        String selfieCamera = cameraParts[1].trim();
        System.out.println(" first selfieCamera = " + selfieCamera);
        if (selfieCamera.equalsIgnoreCase("null")) {
            System.out.println("enter in or not ");
            selfieCamera = null;
        }
        System.out.println("selfieCamera ==== " + selfieCamera);


        BrandEntity brandName = this.brandRepository.findByBrandName(productDraftEntity.getBrand())
                .orElseThrow(() -> new CustomException(ExceptionEnum.BRAND_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        ModelEntity modelName = this.modelRepository.findByDeviceName(productDraftEntity.getModelName())
                .orElseThrow(() -> new CustomException(ExceptionEnum.MODEL_NAME_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        System.out.println("modelName.getId() = " + modelName.getId());

        ColorEntity color = this.colorRepository.findByColor(productDraftEntity.getColor())
                .orElseThrow(() -> new CustomException(ExceptionEnum.COLOR_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        RamStorageEntity ramStorage = this.ramRepository.findByRamStorage(productDraftEntity.getRamStorage())
                .orElseThrow(() -> new CustomException(ExceptionEnum.RAM_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        InternalStorageEntity internalStorage = this.internalStorageRepository.findByInternalStorage(productDraftEntity.getInternalStorage())
                .orElseThrow(() -> new CustomException(ExceptionEnum.INTERNAL_STORAGE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        CameraEntity camera = this.cameraRepository.findCameraByModelId(modelName.getId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.CAMERA_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        System.out.println("final camera = " + camera);

        BatteryEntity battery = this.batteryRepository.findByBatteryCapacity(productDraftEntity.getBattery())
                .orElseThrow(() -> new CustomException(ExceptionEnum.BATTERY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        ImageEntity image = this.imageRepository.findImageByModelId(modelName.getId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.IMAGE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

                OperatingSystemEntity
        operatingSystem = this.operatingSystemRepository.findByOperatingSystem(productDraftEntity.getOperatingSystem())
                .orElseThrow(() -> new CustomException(ExceptionEnum.OPERATING_SYSTEM_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        UserEntity user = this.userRepository.findById(productDraftEntity.getUserId().getId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

        ProductEntity productEntity;

        if (productDraftEntity.getProductId() != null) {
            // Update Existing Product
            productEntity = this.productRepository.findById(productDraftEntity.getProductId().getId())
                    .orElseThrow(() -> new CustomException(ExceptionEnum.PRODUCT_ID_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
            saveProduct(productDraftEntity, brandName, modelName, color,image, ramStorage, internalStorage, camera, battery, operatingSystem, user, currentUser, productEntity);
        } else {
            // Create a Product based on Product Draft:
            productEntity = new ProductEntity();
            saveProduct(productDraftEntity, brandName, modelName, color, image, ramStorage, internalStorage, camera, battery, operatingSystem, user, currentUser, productEntity);
        }
    }

    //For save Product :
    private void saveProduct(ProductDraftEntity productDraftEntity, BrandEntity brandName, ModelEntity modelName, ColorEntity color,ImageEntity image, RamStorageEntity ramStorage, InternalStorageEntity internalStorage, CameraEntity camera, BatteryEntity battery, OperatingSystemEntity operatingSystem, UserEntity user, UserEntity currentUser, ProductEntity productEntity) {
        productEntity.setPrice(productDraftEntity.getPrice());
        productEntity.setModelId(modelName);
        productEntity.setBrandId(brandName);
        productEntity.setColorId(color);
        productEntity.setImageId(image);
        productEntity.setRamStorageId(ramStorage);
        productEntity.setInternalStorageId(internalStorage);
        productEntity.setCameraId(camera);
        productEntity.setBatteryId(battery);
        productEntity.setOperatingSystemId(operatingSystem);
        productEntity.setUserId(user);
        productEntity.setCreatedBy(currentUser);
        productEntity.setUpdatedBy(currentUser);
        // Save the Product
        this.productRepository.save(productEntity);
    }
}

