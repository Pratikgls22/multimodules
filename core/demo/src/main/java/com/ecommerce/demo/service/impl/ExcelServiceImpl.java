package com.ecommerce.demo.service.impl;


import com.ecommerce.demo.service.ExcelService;
import com.ecommerce.demo.utill.Utilities;
import com.ecommerce.entity.elasticSerachEntity.*;
import com.ecommerce.entity.entity.*;
import com.ecommerce.entity.requestDto.ExcelRequestDto;
import com.ecommerce.entity.requestDto.esRequestDto.EsExcelRequestDto;
import com.ecommerce.repository.elasticSearchRepository.*;
import com.ecommerce.repository.jpaRepository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    private final ProductRepository productRepository;
    private final EsProductRepository esProductRepository;

    private final MainCategoryRepository categoryRepository;
    private final EsMainCategoryRepository esMainCategoryRepository;

    private final ModelRepository modelRepository;
    private final EsModelRepository esModelRepository;

    private final RamRepository ramRepository;
    private final EsRamRepository esRamRepository;

    private final ColorRepository colorRepository;
    private final EsColorRepository esColorRepository;

    private final InternalStorageRepository internalStorageRepository;
    private final EsInternalStorageRepository esInternalStorageRepository;

    private final Utilities utilities;

    private final BrandRepository brandRepository;
    private final EsBrandRepository esBrandRepository;

    private final NetworkRepository networkRepository;
    private final EsNetworkRepository esNetworkRepository;

    private final SimSlotRepository simSlotRepository;
    private final EsSimSlotRepository esSimSlotRepository;

    private final ScreenSizeRepository screenSizeRepository;
    private final EsScreenSizeRepository esScreenSizeRepository;

    private final BatteryCapacityRepository batteryCapacityRepository;
    private final EsBatteryCapacityRepository esBatteryCapacityRepository;

    private final ProcessorRepository processorRepository;
    private final EsProcessorRepository esProcessorRepository;

    /**
     * @param multipartFile <>this method read excel and store the data</>
     * @throws IOException
     */
    @Override
    public void readFileAndSave(MultipartFile multipartFile) throws IOException {

        Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        UserEntity currentUser = utilities.currentUser();

        for (Row row : sheet) {
//            skip header
            if (row.getRowNum() != 0) {
                String category = row.getCell(0).getStringCellValue();
                String brand = row.getCell(1).getStringCellValue();
                String model = row.getCell(2).getStringCellValue();
                String colorNames = row.getCell(3).getStringCellValue();
                String rams = row.getCell(4).getStringCellValue();
                String internalStorages = row.getCell(5).getStringCellValue();
                String network = row.getCell(6).getStringCellValue();
                String simSlot = row.getCell(7).getStringCellValue();
                String screenSize = row.getCell(8).getStringCellValue();
                String battery = row.getCell(9).getStringCellValue();
                String processor = row.getCell(10).getStringCellValue();

                // find in category master if not then save
                var categoryEntity = this.categoryRepository.findByCategoryName(category).orElseGet(
                        () -> saveOrCreateCategoryEntity(category, currentUser));

                var brandEntity = this.brandRepository.findByBrandName(brand).orElseGet(
                        () -> saveOrCreateBrandEntity(brand, currentUser, categoryEntity));

                var modelEntity = this.modelRepository.findByModalName(model).orElseGet(
                        () -> saveOrCreateModelEntity(model, categoryEntity, currentUser));

                var colorEntityList = saveOrCreateColorEntity(colorNames, currentUser, categoryEntity);

                var ramEntityList = saveOrCreateRamEntity(rams, currentUser, categoryEntity);

                var internalStorageList = saveOrCreateInternalStorageEntities(internalStorages, currentUser, categoryEntity);

                var networkEntity = this.networkRepository.findByNetworkType(network).orElseGet(
                        () -> saveOrCreateNetworkEntity(network, categoryEntity, currentUser));

                var simSlotEntity = this.simSlotRepository.findBySimSlotType(simSlot).orElseGet(
                        () -> saveOrCreateSimSlotTypeEntity(simSlot, categoryEntity, currentUser));

                var screenSizeEntity = this.screenSizeRepository.findByScreenSize(screenSize).orElseGet(
                        () -> saveOrCreateScreenSizeEntity(screenSize, categoryEntity, currentUser));

                var batteryEntity = this.batteryCapacityRepository.findByBatteryCapacity(battery).orElseGet(
                        () -> saveOrCreateBattery(battery, categoryEntity, currentUser));

                var processorEntity = this.processorRepository.findByProcessorName(processor).orElseGet(
                        () -> saveOrCreateProcessor(processor, categoryEntity, currentUser));

                // *****  For ElasticSearch  ***** //
                var esMainCategoryEntity = this.esMainCategoryRepository.findByCategoryName(category).orElseGet(
                        () -> saveOrCreateEsCategoryEntity(category, currentUser));

                var esBrandEntity = this.esBrandRepository.findByBrandName(brand).orElseGet(
                        () -> saveOrCreateEsBrandEntity(brand, currentUser, esMainCategoryEntity));

                var esModelEntity = this.esModelRepository.findByModalName(model).orElseGet(
                        () -> saveOrCreateEsModelEntity(model, esMainCategoryEntity, currentUser));

                var esColorEntityList = saveOrCreateEsColorEntity(colorNames, currentUser, esMainCategoryEntity);

                var esRamEntityList = saveOrCreateEsRamEntity(rams, currentUser, esMainCategoryEntity);

                var esInternalStorageList = saveOrCreateEsInternalStorageEntities(internalStorages, currentUser, esMainCategoryEntity);

                var esNetworkEntity = this.esNetworkRepository.findByNetworkType(network).orElseGet(
                        () -> saveOrCreateEsNetworkEntity(network, esMainCategoryEntity, currentUser));

                var esSimSlotEntity = this.esSimSlotRepository.findBySimSlotType(simSlot).orElseGet(
                        () -> saveOrCreateEsSimSlotTypeEntity(simSlot, esMainCategoryEntity, currentUser));

                var esScreenSizeEntity = this.esScreenSizeRepository.findByScreenSize(screenSize).orElseGet(
                        () -> saveOrCreateEsScreenSizeEntity(screenSize, esMainCategoryEntity, currentUser));

                var esBatteryEntity = this.esBatteryCapacityRepository.findByBatteryCapacity(battery).orElseGet(
                        () -> saveOrCreateEsBattery(battery, esMainCategoryEntity, currentUser));

                var esProcessorEntity = this.esProcessorRepository.findByProcessorName(processor).orElseGet(
                        () -> saveOrCreateEsProcessor(processor, esMainCategoryEntity, currentUser));

                List<ProductEntity> productEntities = new ArrayList<>();

                // Iterate over each color
                for (ColorEntity colorEntity : colorEntityList) {
                    // Iterate over each RAM value
                    for (RamEntity ramEntity : ramEntityList) {
                        // Iterate over each internal storage value
                        for (InternalStorageEntity internalStorageEntity : internalStorageList) {
                            // Create a new ProductEntity for each combination
                            ProductEntity productEntity = new ProductEntity(categoryEntity, modelEntity, colorEntityList, ramEntityList, internalStorageList, brandEntity, simSlotEntity, batteryEntity, screenSizeEntity, processorEntity, networkEntity);
                            productEntity.setColorEntity(colorEntity);              // Set color
                            productEntity.setInternalStorageEntity(internalStorageEntity); // Set internal storage
                            productEntity.setRamEntity(ramEntity);                  // Set RAM
                            productEntity.setUpdatedBy(currentUser);
                            productEntity.setCreatedBy(currentUser);
                            productEntity.setMainCategoryEntity(categoryEntity);    // Set category
                            productEntity.setModelEntity(modelEntity);              // Set model
                            productEntity.setBrandEntity(brandEntity);               // Set brand
                            productEntity.setSimSlotEntity(simSlotEntity);              // Set sim slot
                            productEntity.setBatteryCapacity(batteryEntity);            // Set battery size
                            productEntity.setScreenSizeEntity(screenSizeEntity);         // Set screen size
                            productEntity.setProcessorEntity(processorEntity);          // set processor
                            productEntity.setNetworkEntity(networkEntity);              // set network

                            // Create a DTO to check if this product combination already exists
                            ExcelRequestDto excelRequestDto = new ExcelRequestDto(categoryEntity,modelEntity,colorEntity,ramEntity,internalStorageEntity,brandEntity,simSlotEntity,batteryEntity,screenSizeEntity,processorEntity,networkEntity);

                            // Check if the combination exists
                            var entity = this.productRepository.findByAttributes(excelRequestDto);
                            if (entity.isEmpty()) {
                                // If the product combination does not exist, add to list for saving
                                productEntities.add(productEntity);
                            }
                        }
                    }
                }

                //*******  For ElasticSearch  *********//

                List<EsProductEntity> esProductEntities = new ArrayList<>();

                // Iterate over each color
                for (EsColorEntity esColorEntity : esColorEntityList) {
                    // Iterate over each RAM value
                    for (EsRamEntity esRamEntity : esRamEntityList) {
                        // Iterate over each internal storage value
                        for (EsInternalStorageEntity esInternalStorageEntity : esInternalStorageList) {
                            // Create a new ProductEntity for each combination
                            EsProductEntity esProductEntity = new EsProductEntity(esMainCategoryEntity, esModelEntity, esColorEntityList, esRamEntityList, esInternalStorageList, esBrandEntity, esSimSlotEntity, esBatteryEntity, esScreenSizeEntity, esProcessorEntity, esNetworkEntity);
                            esProductEntity.setColorEntity(esColorEntity);              // Set color
                            esProductEntity.setInternalStorageEntity(esInternalStorageEntity); // Set internal storage
                            esProductEntity.setRamEntity(esRamEntity);                  // Set RAM
                            esProductEntity.setUpdatedBy(currentUser);
                            esProductEntity.setCreatedBy(currentUser);
                            esProductEntity.setEsMainCategoryEntity(esMainCategoryEntity);    // Set category
                            esProductEntity.setModelEntity(esModelEntity);              // Set model
                            esProductEntity.setBrandEntity(esBrandEntity);               // Set brand
                            esProductEntity.setSimSlotEntity(esSimSlotEntity);              // Set sim slot
                            esProductEntity.setBatteryCapacity(esBatteryEntity);            // Set battery size
                            esProductEntity.setScreenSizeEntity(esScreenSizeEntity);         // Set screen size
                            esProductEntity.setProcessorEntity(esProcessorEntity);          // set processor
                            esProductEntity.setNetworkEntity(esNetworkEntity);              // set network

                            // Create a DTO to check if this product combination already exists
                            EsExcelRequestDto esExcelRequestDto = new EsExcelRequestDto(esMainCategoryEntity,esModelEntity,esColorEntity,esRamEntity,esInternalStorageEntity,esBrandEntity,esSimSlotEntity,esBatteryEntity,esScreenSizeEntity,esProcessorEntity,esNetworkEntity);

                            // Check if the combination exists
                            var esEntity = this.esProductRepository.findByAttributes(esExcelRequestDto);
                            if (esEntity.isEmpty()) {
                                // If the product combination does not exist, add to list for saving
                                esProductEntities.add(esProductEntity);
                            }
                        }
                    }
                }

// Save all new product combinations to the repository
                productRepository.saveAll(productEntities);
                esProductRepository.saveAll(esProductEntities);

            }
        }
    }

    private List<ColorEntity> saveOrCreateColorEntity(String colorNames, UserEntity currentUser, MainCategoryEntity categoryEntity) {
        List<String> items = Arrays.asList(colorNames.split("\\s*,\\s*"));
        List<ColorEntity> colorEntityList = new ArrayList<>();
        List<ColorEntity> newColorEntityList = new ArrayList<>();
        items.forEach(r -> {
            var colorE = this.colorRepository.findByColor(r);
            if (colorE.isPresent()) {
                colorEntityList.add(colorE.get());
            } else {
                ColorEntity colorEntity = new ColorEntity();
                colorEntity.setMainCategoryEntity(categoryEntity);
                colorEntity.setCreatedBy(currentUser);
                colorEntity.setUpdatedBy(currentUser);
                colorEntity.setColor(r);
                newColorEntityList.add(colorEntity);
            }
        });
        if (newColorEntityList.isEmpty()){
            return colorEntityList;
        }
        colorEntityList.addAll(this.colorRepository.saveAll(newColorEntityList));
        return colorEntityList;
    }

    private List<RamEntity> saveOrCreateRamEntity(String ramNames, UserEntity currentUser, MainCategoryEntity categoryEntity) {
        List<String> items = Arrays.asList(ramNames.split("\\s*,\\s*"));
        List<RamEntity> ramEntityList = new ArrayList<>();
        List<RamEntity> newRamEntityList = new ArrayList<>();
        items.forEach(r -> {
            var ramLists = this.ramRepository.findByRam(r);
            if (ramLists.isPresent()) {
                ramEntityList.add(ramLists.get());
            } else {
                RamEntity ramEntity = new RamEntity();
                ramEntity.setMainCategoryEntity(categoryEntity);
                ramEntity.setCreatedBy(currentUser);
                ramEntity.setUpdatedBy(currentUser);
                ramEntity.setRam(r);
                newRamEntityList.add(ramEntity);
            }
        });
        if (newRamEntityList.isEmpty()){
            return ramEntityList;
        }
        ramEntityList.addAll(this.ramRepository.saveAll(newRamEntityList));
        return ramEntityList;
    }

    private List<InternalStorageEntity> saveOrCreateInternalStorageEntities(String internalStorageNames, UserEntity currentUser, MainCategoryEntity categoryEntity) {
        List<String> items = Arrays.asList(internalStorageNames.split("\\s*,\\s*"));
        List<InternalStorageEntity> internalStorageEntities = new ArrayList<>();
        List<InternalStorageEntity> newInternalStorageEntities = new ArrayList<>();
        items.forEach(r -> {
            var internalStorageLists = this.internalStorageRepository.findByInternalStorage(r);
            if (internalStorageLists.isEmpty()) {
                InternalStorageEntity internalStorageEntity = new InternalStorageEntity();
                internalStorageEntity.setMainCategoryEntity(categoryEntity);
                internalStorageEntity.setCreatedBy(currentUser);
                internalStorageEntity.setUpdatedBy(currentUser);
                internalStorageEntity.setInternalStorage(r);
                newInternalStorageEntities.add(internalStorageEntity);
            } else {
                internalStorageEntities.add(internalStorageLists.get());
            }
        });
        if (newInternalStorageEntities.isEmpty()){
            return internalStorageEntities;
        }
        internalStorageEntities.addAll(this.internalStorageRepository.saveAll(newInternalStorageEntities));
        return internalStorageEntities;
    }


    private ProcessorEntity saveOrCreateProcessor(String processor, MainCategoryEntity categoryEntity, UserEntity currentUser) {
        ProcessorEntity processorEntity = ProcessorEntity.builder()
                .processorName(processor)
                .mainCategoryEntity(categoryEntity)
                .build();
        processorEntity.setCreatedBy(currentUser);
        processorEntity.setUpdatedBy(currentUser);
        return this.processorRepository.save(processorEntity);
    }

    private BatteryCapacityEntity saveOrCreateBattery(String battery, MainCategoryEntity categoryEntity, UserEntity currentUser) {
        BatteryCapacityEntity batteryCapacityEntity = BatteryCapacityEntity.builder()
                .batteryCapacity(battery)
                .mainCategoryEntity(categoryEntity)
                .build();
        batteryCapacityEntity.setCreatedBy(currentUser);
        batteryCapacityEntity.setUpdatedBy(currentUser);
        return this.batteryCapacityRepository.save(batteryCapacityEntity);
    }

    private ScreenSizeEntity saveOrCreateScreenSizeEntity(String screenSize, MainCategoryEntity categoryEntity, UserEntity currentUser) {
        ScreenSizeEntity screenSizeEntity = ScreenSizeEntity.builder()
                .screenSize(screenSize)
                .mainCategoryEntity(categoryEntity)
                .build();
        screenSizeEntity.setCreatedBy(currentUser);
        screenSizeEntity.setUpdatedBy(currentUser);
        return this.screenSizeRepository.save(screenSizeEntity);
    }

    private SimSlotEntity saveOrCreateSimSlotTypeEntity(String simSlot, MainCategoryEntity categoryEntity, UserEntity currentUser) {
        SimSlotEntity simSlotEntity = SimSlotEntity.builder()
                .simSlotType(simSlot)
                .mainCategoryEntity(categoryEntity)
                .build();
        simSlotEntity.setCreatedBy(currentUser);
        simSlotEntity.setUpdatedBy(currentUser);
        return this.simSlotRepository.save(simSlotEntity);

    }

    private NetworkEntity saveOrCreateNetworkEntity(String network, MainCategoryEntity categoryEntity, UserEntity currentUser) {
        NetworkEntity networkEntity = NetworkEntity.builder()
                .networkType(network)
                .mainCategoryEntity(categoryEntity)
                .build();
        networkEntity.setCreatedBy(currentUser);
        networkEntity.setUpdatedBy(currentUser);
        return this.networkRepository.save(networkEntity);
    }

    private BrandEntity saveOrCreateBrandEntity(String brand, UserEntity currentUser, MainCategoryEntity categoryEntity) {
        BrandEntity brandEntity = BrandEntity.builder()
                .brandName(brand)
                .mainCategoryEntity(categoryEntity)
                .build();
        brandEntity.setCreatedBy(currentUser);
        brandEntity.setUpdatedBy(currentUser);
        return this.brandRepository.save(brandEntity);
    }

    private MainCategoryEntity saveOrCreateCategoryEntity(String categoryName, UserEntity currentUser) {
        MainCategoryEntity categoryEntity = MainCategoryEntity.builder()
                .categoryName(categoryName)
                .build();
        categoryEntity.setCreatedBy(currentUser);
        categoryEntity.setUpdatedBy(currentUser);
        // save into database
        return this.categoryRepository.save(categoryEntity);
    }

    private ModelEntity saveOrCreateModelEntity(String model, MainCategoryEntity categoryEntity, UserEntity currentUser) {
        ModelEntity modelEntity = ModelEntity.builder()
                .modalName(model)
                .mainCategoryEntity(categoryEntity).build();
        modelEntity.setCreatedBy(currentUser);
        modelEntity.setUpdatedBy(currentUser);
        return this.modelRepository.save(modelEntity);
    }


    // ***********  For ElasticSEarch  ************ //

    private List<EsColorEntity> saveOrCreateEsColorEntity(String colorNames, UserEntity currentUser, EsMainCategoryEntity esMainCategoryEntity) {
        List<String> items = Arrays.asList(colorNames.split("\\s*,\\s*"));
        List<EsColorEntity> esColorEntityList = new ArrayList<>();
        List<EsColorEntity> newEsColorEntityList = new ArrayList<>();

        items.forEach(r -> {
            var esColorE = this.esColorRepository.findByColor(r);
            if (esColorE.isPresent()) {
                esColorEntityList.add(esColorE.get());
            } else {
                EsColorEntity esColorEntity = new EsColorEntity();
                esColorEntity.setEsMainCategoryEntity(esMainCategoryEntity);  // Set the category
                esColorEntity.setCreatedBy(currentUser);               // Set the user who created it
                esColorEntity.setUpdatedBy(currentUser);               // Set the user who last updated it
                esColorEntity.setColor(r);                            // Set the color name
                newEsColorEntityList.add(esColorEntity);              // Add the new entity to the list
            }
        });

        if (newEsColorEntityList.isEmpty()) {
            return esColorEntityList;
        }
        esColorEntityList.addAll((java.util.Collection<? extends EsColorEntity>) this.esColorRepository.saveAll(newEsColorEntityList));
        return esColorEntityList;
    }

    private List<EsRamEntity> saveOrCreateEsRamEntity(String ramNames, UserEntity currentUser, EsMainCategoryEntity esMainCategoryEntity) {
        List<String> items = Arrays.asList(ramNames.split("\\s*,\\s*"));
        List<EsRamEntity> esRamEntityList = new ArrayList<>();
        List<EsRamEntity> newEsRamEntityList = new ArrayList<>();

        items.forEach(r -> {
            var esRam = this.esRamRepository.findByRam(r);
            if (esRam.isPresent()) {
                esRamEntityList.add(esRam.get());
            } else {
                EsRamEntity esRamEntity = new EsRamEntity();
                esRamEntity.setEsMainCategoryEntity(esMainCategoryEntity);
                esRamEntity.setCreatedBy(currentUser);
                esRamEntity.setUpdatedBy(currentUser);
                esRamEntity.setRam(r);
                newEsRamEntityList.add(esRamEntity);
            }
        });

        if (newEsRamEntityList.isEmpty()) {
            return esRamEntityList;
        }

        esRamEntityList.addAll((java.util.Collection<? extends EsRamEntity>) this.esRamRepository.saveAll(newEsRamEntityList));
        return esRamEntityList;
    }

    private List<EsInternalStorageEntity> saveOrCreateEsInternalStorageEntities(String internalStorageNames, UserEntity currentUser, EsMainCategoryEntity esMainCategoryEntity) {
        List<String> items = Arrays.asList(internalStorageNames.split("\\s*,\\s*"));
        List<EsInternalStorageEntity> esInternalStorageEntities = new ArrayList<>();
        List<EsInternalStorageEntity> newEsInternalStorageEntities = new ArrayList<>();

        items.forEach(r -> {
            var esInternalStorage = this.esInternalStorageRepository.findByInternalStorage(r);
            if (esInternalStorage.isPresent()) {
                esInternalStorageEntities.add(esInternalStorage.get());
            } else {
                EsInternalStorageEntity esInternalStorageEntity = new EsInternalStorageEntity();
                esInternalStorageEntity.setEsMainCategoryEntity(esMainCategoryEntity);
                esInternalStorageEntity.setCreatedBy(currentUser);
                esInternalStorageEntity.setUpdatedBy(currentUser);
                esInternalStorageEntity.setInternalStorage(r);
                newEsInternalStorageEntities.add(esInternalStorageEntity);
            }
        });

        if (newEsInternalStorageEntities.isEmpty()) {
            return esInternalStorageEntities;
        }

        esInternalStorageEntities.addAll((java.util.Collection<? extends EsInternalStorageEntity>) this.esInternalStorageRepository.saveAll(newEsInternalStorageEntities));
        return esInternalStorageEntities;
    }

    private EsMainCategoryEntity saveOrCreateEsCategoryEntity(String categoryName, UserEntity currentUser) {
        EsMainCategoryEntity esCategoryEntity = EsMainCategoryEntity.builder()
                .categoryName(categoryName)
                .build();
        esCategoryEntity.setCreatedBy(currentUser);
        esCategoryEntity.setUpdatedBy(currentUser);
        return this.esMainCategoryRepository.save(esCategoryEntity);
    }

    private EsBrandEntity saveOrCreateEsBrandEntity(String brand, UserEntity currentUser, EsMainCategoryEntity esMainCategoryEntity) {
        EsBrandEntity esBrandEntity = EsBrandEntity.builder()
                .brandName(brand)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esBrandEntity.setCreatedBy(currentUser);
        esBrandEntity.setUpdatedBy(currentUser);
        return this.esBrandRepository.save(esBrandEntity);
    }

    private EsModelEntity saveOrCreateEsModelEntity(String model, EsMainCategoryEntity esMainCategoryEntity, UserEntity currentUser) {
        EsModelEntity esModelEntity = EsModelEntity.builder()
                .modalName(model)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esModelEntity.setCreatedBy(currentUser);
        esModelEntity.setUpdatedBy(currentUser);
        return this.esModelRepository.save(esModelEntity);
    }

    private EsNetworkEntity saveOrCreateEsNetworkEntity(String network, EsMainCategoryEntity esMainCategoryEntity, UserEntity currentUser) {
        EsNetworkEntity esNetworkEntity = EsNetworkEntity.builder()
                .networkType(network)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esNetworkEntity.setCreatedBy(currentUser);
        esNetworkEntity.setUpdatedBy(currentUser);
        return this.esNetworkRepository.save(esNetworkEntity);
    }

    private EsSimSlotEntity saveOrCreateEsSimSlotTypeEntity(String simSlot, EsMainCategoryEntity esMainCategoryEntity, UserEntity currentUser) {
        EsSimSlotEntity esSimSlotEntity = EsSimSlotEntity.builder()
                .simSlotType(simSlot)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esSimSlotEntity.setCreatedBy(currentUser);
        esSimSlotEntity.setUpdatedBy(currentUser);
        return this.esSimSlotRepository.save(esSimSlotEntity);
    }

    private EsScreenSizeEntity saveOrCreateEsScreenSizeEntity(String screenSize, EsMainCategoryEntity esMainCategoryEntity, UserEntity currentUser) {
        EsScreenSizeEntity esScreenSizeEntity = EsScreenSizeEntity.builder()
                .screenSize(screenSize)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esScreenSizeEntity.setCreatedBy(currentUser);
        esScreenSizeEntity.setUpdatedBy(currentUser);
        return this.esScreenSizeRepository.save(esScreenSizeEntity);
    }

    private EsBatteryCapacityEntity saveOrCreateEsBattery(String battery, EsMainCategoryEntity esMainCategoryEntity, UserEntity currentUser) {
        EsBatteryCapacityEntity esBatteryCapacityEntity = EsBatteryCapacityEntity.builder()
                .batteryCapacity(battery)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esBatteryCapacityEntity.setCreatedBy(currentUser);
        esBatteryCapacityEntity.setUpdatedBy(currentUser);
        return this.esBatteryCapacityRepository.save(esBatteryCapacityEntity);
    }

    private EsProcessorEntity saveOrCreateEsProcessor(String processor, EsMainCategoryEntity esMainCategoryEntity, UserEntity currentUser) {
        EsProcessorEntity esProcessorEntity = EsProcessorEntity.builder()
                .processorName(processor)
                .esMainCategoryEntity(esMainCategoryEntity)
                .build();
        esProcessorEntity.setCreatedBy(currentUser);
        esProcessorEntity.setUpdatedBy(currentUser);
        return this.esProcessorRepository.save(esProcessorEntity);
    }

    //For Get Data for Elastic search Repository

    @Override
    public void getAllEsExcelData() {
        this.esProductRepository.findAll();
    }
}