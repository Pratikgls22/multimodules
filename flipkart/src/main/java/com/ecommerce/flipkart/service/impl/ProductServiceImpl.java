package com.ecommerce.flipkart.service.impl;

import com.ecommerce.flipkart.dto.ProductRequestDto;
import com.ecommerce.flipkart.entity.*;
import com.ecommerce.flipkart.service.ExcelHelperService;
import com.ecommerce.flipkart.repositry.*;
import com.ecommerce.flipkart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String CSV_FILE_LOCATION = "/home/dev1071/Downloads/products.xlsx";

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ModelRepository modelRepository;
    private final ColorRepository colorRepository;
    private final InternalStorageRepository internalStorageRepository;
    private final RamRepository ramRepository;
    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    @Override
    public void adduser(ProductRequestDto productRequestDto) {
        CategoryEntity categoryEntityName = mainCategoryRepository.findByName(productRequestDto.getMainCategoryName())
                .orElseGet(() -> createNewMainCategory(productRequestDto.getMainCategoryName()));


        SubCategoryEntity subCategoryEntityName = subCategoryRepository.findByName(productRequestDto.getSubCategoryName())
                .orElseGet(() -> createNewSubCategory(productRequestDto.getSubCategoryName()));

        ModelEntity modelEntityName = modelRepository.findByName(productRequestDto.getModelName())
                .orElseGet(() -> createNewModel(productRequestDto.getModelName()));

        RamEntity ramEntitySize = ramRepository.findBySize(productRequestDto.getRamSize())
                .orElseGet(() -> createNewRam(productRequestDto.getRamSize()));

        ColorEntity colorEntity = colorRepository.findByName(productRequestDto.getColorName())
                .orElseGet(() -> createNewColor(productRequestDto.getColorName()));

        InternalStorageEntity internalStorage = internalStorageRepository.findBySize(productRequestDto.getStorageSize())
                .orElseGet(() -> createNewStorageSize(productRequestDto.getStorageSize()));

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPrice(productRequestDto.getPrice());
        this.priceRepository.save(priceEntity);

    }

    private InternalStorageEntity createNewStorageSize(String storageSize) {
        InternalStorageEntity storage = new InternalStorageEntity();
        storage.setStorageSize(storageSize);
        return internalStorageRepository.save(storage);
    }

    private ColorEntity createNewColor(String colorName) {
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setColorName(colorName);
        return colorRepository.save(colorEntity);
    }

    private RamEntity createNewRam(String ramSize) {
        RamEntity ramEntity = new RamEntity();
        ramEntity.setRamSize(ramSize);
        return ramRepository.save(ramEntity);
    }

    private ModelEntity createNewModel(String modelName) {
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setModelName(modelName);
        return modelRepository.save(modelEntity);
    }

    private SubCategoryEntity createNewSubCategory(String subCategoryName) {
        SubCategoryEntity subCategoryEntity = new SubCategoryEntity();
        subCategoryEntity.setSubName(subCategoryName);
        return subCategoryRepository.save(subCategoryEntity);
    }

    private CategoryEntity createNewMainCategory(String mainCategoryName) {
            CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setProductName(mainCategoryName);
        return mainCategoryRepository.save(categoryEntity);
    }

    @Override
    public void saveCustomersToDatabase(MultipartFile file){
        if(ExcelHelperService.isValidExcelFile(file)){
            try {
                List<ProductEntity> productEntities = ExcelHelperService.getCustomersDataFromExcel(file.getInputStream());
                this.productRepository.saveAll(productEntities);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

}
