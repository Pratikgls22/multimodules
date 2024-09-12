package com.ecommerce.demo.service.impl;

import com.ecommerce.demo.service.ExcelService;
import com.ecommerce.demo.utill.Utilities;
import com.ecommerce.entity.entity.*;
import com.ecommerce.entity.requestDto.ExcelRequestDto;
import com.ecommerce.repository.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final CategoryRepository categoryRepository;
    private final ModelRepository modelRepository;
    private final RamRepository ramRepository;
    private final ColorRepository colorRepository;
    private final InternalStorageRepository internalStorageRepository;
    private final Utilities utilities;

    @Override
    public void readFileAndSave(MultipartFile multipartFile) throws IOException {

        Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);


        Map<String, Long> categoryMap = new HashMap<>();
        Map<String, Long> modelMap = new HashMap<>();
        Map<String, Long> colorMap = new HashMap<>();
        Map<String, Long> ramMap = new HashMap<>();
        Map<String, Long> internalStorageMap = new HashMap<>();


        UserEntity currentUser = utilities.currentUser();

        int lastRow = sheet.getLastRowNum();
        for (Row row : sheet) {
            System.out.println(row.getRowNum());
//            skip header
            if (row.getRowNum() != 0 ) {

                String category = row.getCell(0).getStringCellValue();
                String subCategory = row.getCell(1).getStringCellValue();
                String model = row.getCell(2).getStringCellValue();
                String ram = row.getCell(3).getStringCellValue();
                String internalStorage = row.getCell(4).getStringCellValue();
                String color = row.getCell(5).getStringCellValue();
                int price =(int) row.getCell(6).getNumericCellValue();

                var categoryEntity = this.categoryRepository.findByCategoryName(category)
                        .orElseGet(() ->
                            saveOrCreateCategory(category,currentUser));

                Optional<CategoryEntity> subCategoryEntity = this.categoryRepository.findByCategoryName(subCategory);
                System.out.println("subCategoryEntity = " + subCategoryEntity);

                var modelEntity = this.modelRepository.findByModalName(model)
                        .orElseGet(() -> saveOrCreateModel(model,categoryEntity,currentUser));

                var colorEntity = this.colorRepository.findByColor(color)
                        .orElseGet(() -> saveOrCreateColor(color,categoryEntity,currentUser));

                var ramEntity = this.ramRepository.findByRam(ram)
                        .orElseGet(() -> saveOrCreateRam(ram,categoryEntity,currentUser));

                var internalStorageEntity = this.internalStorageRepository.findByInternalStorage(internalStorage)
                        .orElseGet(() -> saveOrCreateInternalStorage(internalStorage,categoryEntity,currentUser));

                var priceEntity = this.priceRepository.findByAmount(price)
                        .orElseGet(() -> saveOrCreatePrice(price,currentUser));

                ProductEntity productEntity = ProductEntity.builder()
                        .categoryEntity(categoryEntity)
                        .subCategoryEntity(categoryEntity)
                        .modelEntity(modelEntity)
                        .colorEntity(colorEntity)
                        .ramEntity(ramEntity)
                        .internalStorageEntity(internalStorageEntity)
                        .priceEntity(priceEntity)
                        .build();
                productEntity.setCreatedBy(currentUser);
                productEntity.setUpdatedBy(currentUser);

                ExcelRequestDto excelRequestDto = ExcelRequestDto.builder()
                        .categoryEntity(categoryEntity)
                        .subCategoryEntity(categoryEntity)
                        .modelEntity(modelEntity)
                        .colorEntity(colorEntity)
                        .ramEntity(ramEntity)
                        .internalStorageEntity(internalStorageEntity)
                        .priceEntity(priceEntity)
                        .build();
                var excelRequestDto1 = this.productRepository.findByAttributes(excelRequestDto)
                        .orElseGet(() -> saveOrCreateProduct(productEntity));

            }
        }

    }

    private ProductEntity saveOrCreateProduct(ProductEntity productEntity) {
        return this.productRepository.save(productEntity);
    }

    private PriceEntity saveOrCreatePrice(int price, UserEntity currentUser) {
        PriceEntity priceEntity = PriceEntity.builder()
                .amount(price)
                .build();
        priceEntity.setCreatedBy(currentUser);
        priceEntity.setUpdatedBy(currentUser);
        return this.priceRepository.save(priceEntity);
    }

    private InternalStorageEntity saveOrCreateInternalStorage(String internalStorage, CategoryEntity categoryEntity, UserEntity currentUser) {
        InternalStorageEntity internalStorageEntity = InternalStorageEntity.builder()
                .internalStorage(internalStorage)
                .categoryEntity(categoryEntity)
                .build();
        internalStorageEntity.setCreatedBy(currentUser);
        internalStorageEntity.setUpdatedBy(currentUser);
        return this.internalStorageRepository.save(internalStorageEntity);
    }

    private RamEntity saveOrCreateRam(String ram, CategoryEntity categoryEntity, UserEntity currentUser) {
        RamEntity ramEntity = RamEntity.builder()
                .ram(ram)
                .categoryEntity(categoryEntity)
                .build();
        ramEntity.setCreatedBy(currentUser);
        ramEntity.setUpdatedBy(currentUser);
        return this.ramRepository.save(ramEntity);
    }

    private ColorEntity saveOrCreateColor(String color, CategoryEntity categoryEntity, UserEntity currentUser) {
        ColorEntity colorEntity = ColorEntity.builder()
                .color(color)
                .categoryEntity(categoryEntity)
                .build();
        colorEntity.setCreatedBy(currentUser);
        colorEntity.setUpdatedBy(currentUser);
        return this.colorRepository.save(colorEntity);
    }

    private ModelEntity saveOrCreateModel(String model, CategoryEntity categoryEntity, UserEntity currentUser) {
        ModelEntity modelEntity = ModelEntity.builder()
                .modalName(model)
                .categoryEntity(categoryEntity)
                .build();
        modelEntity.setCreatedBy(currentUser);
        modelEntity.setUpdatedBy(currentUser);
        return this.modelRepository.save(modelEntity);
    }

    private CategoryEntity saveOrCreateCategory(String category, UserEntity currentUser) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(category)
                .build();
        categoryEntity.setCreatedBy(currentUser);
        categoryEntity.setUpdatedBy(currentUser);
        return this.categoryRepository.save(categoryEntity);
    }

    private Long getOrCreateInternalStorage(String internalStorage, Long categoryId, Map<String, Long> internalStorageMap) {
        return internalStorageMap.computeIfAbsent(internalStorage, key -> {
            InternalStorageEntity internalStorageEntity = new InternalStorageEntity();
            internalStorageEntity.setInternalStorage(internalStorage);
            internalStorageEntity.setCategoryEntity(this.categoryRepository.findById(categoryId).orElse(null));
            return this.internalStorageRepository.save(internalStorageEntity).getId();
        });
    }

    private Long getOrCreateRam(String ram, Long categoryId, Map<String, Long> ramMap) {
        return ramMap.computeIfAbsent(ram, key -> {
            RamEntity ramEntity = new RamEntity();
            ramEntity.setRam(ram);
            ramEntity.setCategoryEntity(this.categoryRepository.findById(categoryId).orElse(null));
            return this.ramRepository.save(ramEntity).getId();
        });
    }

    private Long getOrCreateColor(String color, Long categoryId, Map<String, Long> colorMap) {
        return colorMap.computeIfAbsent(color, key -> {
            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setColor(color);
            colorEntity.setCategoryEntity(this.categoryRepository.findById(categoryId).orElse(null));
            return this.colorRepository.save(colorEntity).getId();
        });
    }

    private Long getOrCreateModel(String model, Long categoryId, Map<String, Long> modelMap) {
        return modelMap.computeIfAbsent(model, key -> {
            ModelEntity modelEntity = new ModelEntity();
            modelEntity.setModalName(model);
            modelEntity.setCategoryEntity(this.categoryRepository.findById(categoryId).orElse(null));
            return this.modelRepository.save(modelEntity).getId();
        });
    }

    private Long getOrCreateCategory(String category, Map<String, Long> categoryMap) {
        return categoryMap.computeIfAbsent(category, key -> {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryName(category);
            return this.categoryRepository.save(categoryEntity).getId();
        });
    }
}
