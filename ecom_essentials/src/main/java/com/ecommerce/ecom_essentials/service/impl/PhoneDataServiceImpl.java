package com.ecommerce.ecom_essentials.service.impl;

import com.ecommerce.ecom_essentials.entities.*;
import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import com.ecommerce.ecom_essentials.exception.CustomException;
import com.ecommerce.ecom_essentials.repository.*;
import com.ecommerce.ecom_essentials.responseDto.ModelProjection;
import com.ecommerce.ecom_essentials.service.PhoneDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneDataServiceImpl implements PhoneDataService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BrandRepository brandRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceDetailsRepository deviceDetailsRepository;
    private final SpecificationRepository specificationRepository;
    private final SpecificationDataRepository specificationDataRepository;
    private final PriceRepository priceRepository;
    private final ColorRepository colorRepository;
    private final PictureRepository pictureRepository;
    private final ModelRepository modelRepository;
    private final BatteryRepository batteryRepository;
    private final RAMRepository ramRepository;
    private final InternalStorageRepository internalStorageRepository;
    private final CameraRepository cameraRepository;
    private final OperatingSystemRepository operatingSystemRepository;
    private final CategoryRepository categoryRepository;

//    @Override
//    public void fetchandSavePhoneData() {
//        String url = "https://script.google.com/macros/s/AKfycbxNu27V2Y2LuKUIQMK8lX1y0joB6YmG6hUwB1fNeVbgzEh22TcDGrOak03Fk3uBHmz-/exec?route=brand-list";
//        String response = restTemplate.getForObject(url, String.class);
//
//        try {
//            JsonNode phoneData = objectMapper.readTree(response);
//            System.out.println("phoneData = " + phoneData);
//            if (phoneData.path("status").asInt() == 200) {
//                for (JsonNode data : phoneData.path("data")) {
//                    BrandEntity brandEntity = new BrandEntity();
//                    brandEntity.setBrandName(data.path("brand_name").asText());
//                    brandEntity.setCategoryId();
//                    BrandEntity brand = brandRepository.save(brandEntity);
//                    System.out.println("brand = " + brand);
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse JSON", e);
//        }
//    }

    @Override
    public JsonNode fetchandSaveDeviceList() {
        String deviceListUrl = "https://script.google.com/macros/s/AKfycbxNu27V2Y2LuKUIQMK8lX1y0joB6YmG6hUwB1fNeVbgzEh22TcDGrOak03Fk3uBHmz-/exec?route=device-list";
        String response = restTemplate.getForObject(deviceListUrl, String.class);
        String category = "Phone";

        try {
            CategoryEntity categoryEntity = this.categoryRepository.findByCategory(category)
                    .orElseThrow(() -> new CustomException(ExceptionEnum.CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

            JsonNode deviceData = objectMapper.readTree(response);
            if (deviceData.path("status").asInt() == 200) {
                for (JsonNode brandNode : deviceData.path("data")) {
                    BrandEntity brand = brandRepository.findByBrandName(brandNode.path("brand_name").asText())
                            .orElseGet(() -> {
                                BrandEntity brandEntity = new BrandEntity();
                                brandEntity.setBrandName(brandNode.path("brand_name").asText());
                                brandEntity.setCategoryId(categoryEntity);
                                return this.brandRepository.save(brandEntity);
                            });

                    List<String> deviceKeys = new ArrayList<>();
                    for (JsonNode deviceNode : brandNode.path("device_list")) {
                        String deviceName = deviceNode.path("device_name").asText();
                        String deviceType = deviceNode.path("device_type").asText();
                        String deviceImage = deviceNode.path("device_image").asText();
                        String deviceKey = deviceNode.path("key").asText();

                        DeviceEntity deviceEntity = this.deviceRepository.findByKey(deviceNode.path("key").asText())
                                .orElseGet(() -> {
                                    DeviceEntity newDeviceEntity = new DeviceEntity();
                                    newDeviceEntity.setDeviceName(deviceName);
                                    newDeviceEntity.setDeviceType(deviceType);
                                    newDeviceEntity.setDeviceImage(deviceImage);
                                    newDeviceEntity.setKey(deviceKey);
                                    newDeviceEntity.setBrandId(brand);
                                    newDeviceEntity.setCategoryId(categoryEntity);
                                    return this.deviceRepository.save(newDeviceEntity);
                                });

                        // Also Save in ModelEntity :
                        saveModelInfo(deviceEntity, brand);
                        // Add Key In List :
                        deviceKeys.add(deviceEntity.getKey());
                        System.out.println("deviceKeys = " + deviceKeys);
                    }
                    for (String key : deviceKeys) {
                        if (!key.isEmpty()) {
                            // call Device Details Method :
                            fetchAndSaveDeviceDetail(key, brand, categoryEntity);
                        } else {
                            throw new CustomException(ExceptionEnum.KEY_NOT_EXIST.getValue(), HttpStatus.NOT_FOUND);
                        }
                    }
                }
            }
            return deviceData;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    // Fetch Model Details:
    @Override
    public List<ModelProjection> fetchDetailsOfModel(String brand) {
        return this.deviceDetailsRepository.findByBrandId_BrandName(brand);
    }

    // Fetch All Brand:
    @Override
    public List<String> fetchBrandList() {
        return this.brandRepository.findByBrand();
    }

    @Override
    public List<String> fetchModelByBrand(String brand) {
        return this.modelRepository.findByBrandId_BrandName(brand);
    }

    //@Override
    private JsonNode fetchAndSaveDeviceDetail(String key, BrandEntity brand, CategoryEntity categoryEntity) {
        String deviceListUrl = "https://script.google.com/macros/s/AKfycbxNu27V2Y2LuKUIQMK8lX1y0joB6YmG6hUwB1fNeVbgzEh22TcDGrOak03Fk3uBHmz-/exec";

        // Create Request Body
        String requestBody = "{ \"route\": \"device-detail\", \"key\": \"" + key + "\" }";

        // Set Headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);

        // Make Post Request
        try {
            ResponseEntity<String> response = restTemplate.exchange(deviceListUrl, HttpMethod.POST, entity, String.class);
            System.out.println("response received= " + response.getBody());

            // Get the response body as a plain string
            String responseBody = response.getBody();
            System.out.println("Response received: " + responseBody);

            URI urlOfDataLocation = response.getHeaders().getLocation();
            System.out.println("urlOfDataLocation = " + urlOfDataLocation);

            if (urlOfDataLocation != null) {
                ResponseEntity<String> responseEntity = restTemplate.exchange(urlOfDataLocation, HttpMethod.GET, null, String.class);
                JsonNode deviceDetailsJson = objectMapper.readTree(responseEntity.getBody());
                System.out.println("deviceDetailsJson = " + deviceDetailsJson);

                if (deviceDetailsJson.path("status").asInt() == 200) {
                    JsonNode deviceNode = deviceDetailsJson.path("data");
                    JsonNode moreSpecification = deviceNode.path("more_specification");
                    // Save DeviceDetail :
                    DeviceDetailsEntity deviceDetails = saveDeviceDetails(deviceNode, brand, categoryEntity, moreSpecification);

//                    // Save Model Info:
////                    saveModelInfo(deviceNode, deviceDetails);
//                    saveModelInfo(deviceDetails);
//
//
//                    // Save Operating System data:
//                    saveOperatingSystem(deviceNode, deviceDetails);
//
//                    // For More Specification Data :
//                    JsonNode specificationNode = deviceNode.path("more_specification");
//                    if (specificationNode.isArray()) {
//                        for (JsonNode titleNode : specificationNode) {
//
//                            // Set Title of Specification ;
//                            SpecificationEntity specificationEntity = new SpecificationEntity();
//                            specificationEntity.setTitle(getTextValue(titleNode, "title"));
//                            specificationEntity.setDeviceDetailsId(deviceDetails);
//
//                            // Save Specification :
//                            SpecificationEntity specification = this.specificationRepository.save(specificationEntity);
//                            System.out.println("specificationEntity ********************* " + specificationEntity.getTitle());
//
//
//                            List<SpecificationDataEntity> specDataList = new ArrayList<>();
//
//                            for (JsonNode dataNode : titleNode.path("data")) {
//                                System.out.println("dataNode.getNodeType() ====== " + dataNode.getNodeType());
//                                SpecificationDataEntity specificationDataEntity = new SpecificationDataEntity();
//                                specificationDataEntity.setTitle(getTextValue(dataNode, "title"));
//                                specificationDataEntity.setSpecificationId(specification);
//
//                                //Collect Data values:
//                                //List<String> dataValues = new ArrayList<>();
//                                StringBuilder dataValues = new StringBuilder();
//                                for (JsonNode valueNode : dataNode.path("data")) {
//                                    dataValues.append(valueNode.asText());
//                                }
//                                specificationDataEntity.setData(dataValues.toString());
//                                System.out.println("specificationDataEntity.getTitle() ===== " + specificationDataEntity.getTitle());
//                                System.out.println("specificationDataEntity.getData() ===== " + specificationDataEntity.getData());
//                                specDataList.add(specificationDataEntity);
//                                // Save Specification Data:
//                                SpecificationDataEntity specificationData = this.specificationDataRepository.save(specificationDataEntity);
//
//                                saveSubData(dataNode, deviceDetails);
//
//                                //For Camera Info form Specification :
//                                cameraInfo(specification, specificationData, deviceDetails);
//                            }
//                        }
//                    }
                }
                return deviceDetailsJson;
            } else {
                throw new RuntimeException("No data location URL found.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            throw new RuntimeException("Failed to fetch and parse device details: " + e.getMessage(), e.getCause());
        }
    }

//    private String getTextValue(JsonNode node, String fieldName) {
//        JsonNode valueNode = node.get(fieldName);
//        return valueNode != null ? valueNode.asText() : null;
//    }
//
//    // For Save Operating System Data :
//    private void saveOperatingSystem(JsonNode deviceNode, DeviceDetailsEntity deviceDetails) {
//        JsonNode operatingSystemNode = deviceNode.path("os_type");
//        OperatingSystemEntity operatingSystemEntity = new OperatingSystemEntity();
//        operatingSystemEntity.setOperatingSystem(operatingSystemNode.asText());
//        OperatingSystemEntity system = this.operatingSystemRepository.save(operatingSystemEntity);
//        System.out.println("system =*=*=*=*=*=*=*=*=*=*=*=*=*=*=* " + system);
//    }
//
//
//    // For get Camera Info for Specification :
//    private void cameraInfo(SpecificationEntity specification, SpecificationDataEntity specificationData, DeviceDetailsEntity deviceDetailsEntity) {
//        System.out.println("specification.getTitle()" + specification.getTitle());
//        if (specification.getTitle().equalsIgnoreCase("Main Camera") || specification.getTitle().equalsIgnoreCase("selfie camera")) {
//
//            if (specificationData.getTitle().equalsIgnoreCase("Triple") ||
//                    specificationData.getTitle().equalsIgnoreCase("Dual") ||
//                    specificationData.getTitle().equalsIgnoreCase("Single")) {
//                CameraEntity cameraEntity = cameraRepository.findByDeviceDetailsId(deviceDetailsEntity).orElseGet(() -> {
//                    CameraEntity newCameraEntity = new CameraEntity();
//                    return newCameraEntity;
//                });
//
//                if (specification.getTitle().equalsIgnoreCase("Main Camera")) {
//                    String simplifiedMainCamera = spiltCameraDetails(specificationData.getData());
//                    System.out.println("simplifiedMainCamera /*/*/ = " + simplifiedMainCamera);
//                    var mainCamera = this.cameraRepository.findByMainCamera(simplifiedMainCamera)
//                            .orElseGet(() -> {
//                                CameraEntity newMainCamera = new CameraEntity();
//                                newMainCamera.setMainCamera(simplifiedMainCamera);
//                                return this.cameraRepository.save(newMainCamera);
//                            });
//                    cameraEntity.setMainCamera(mainCamera.getMainCamera());
//                }
//                if (specification.getTitle().equalsIgnoreCase("selfie camera")) {
//                    String simplifiedSelfieCamera = spiltCameraDetails(specificationData.getData());
//                    System.out.println("simplifiedSelfieCamera /*/*/ = " + simplifiedSelfieCamera);
//                    var selfieCamera = this.cameraRepository.findBySelfieCamera(simplifiedSelfieCamera)
//                            .orElseGet(() -> {
//                                CameraEntity newSelfieCamera = new CameraEntity();
//                                newSelfieCamera.setSelfieCamera(simplifiedSelfieCamera);
//                                return this.cameraRepository.save(newSelfieCamera);
//                            });
//                    cameraEntity.setSelfieCamera(selfieCamera.getSelfieCamera());
//                }
//                CameraEntity saveMainCamera = this.cameraRepository.save(cameraEntity);
//                System.out.println("saveMainCamera /*/*/*/*/*/*/*/*/*/*// " + saveMainCamera);
//            }
//
//        }
//    }

//    // For Spilt Camera Details:
//    private String spiltCameraDetails(String cameraDetails) {
//        StringBuilder mpValues = new StringBuilder();
//
//        if (cameraDetails.contains("\n")) {
//            String[] cameraDetail = cameraDetails.split("\n");
//            for (String cameraResolution : cameraDetail) {
//                if (!cameraResolution.trim().isEmpty()) {
//                    splitByComma(cameraResolution, mpValues);
//                }
//            }
//        } else {
//            splitByComma(cameraDetails, mpValues);
//        }
//        return mpValues.toString();
//    }

//    // Split by Comma
//    private void splitByComma(String cameraResolution, StringBuilder mpValues) {
//        String[] detail = cameraResolution.split(",");
//        System.out.println("detail ======================= " + Arrays.toString(detail));
//        String resolution = detail[0].trim();
//        System.out.println("resolution *********============*********** " + resolution);
//        mpValues.append(resolution + " ");
//    }


    // Save DeviceDetail Data :
    private DeviceDetailsEntity saveDeviceDetails(JsonNode deviceData, BrandEntity brand, CategoryEntity categoryEntity, JsonNode moreSpecification) {
        var deviceName = deviceDetailsRepository.findByDeviceName(deviceData.path("device_name").asText())
                .orElseGet(() -> createDeviceDetailsEntity(deviceData, brand, categoryEntity,moreSpecification));
        return deviceName;
    }

    // Create DeviceDetails :
    private DeviceDetailsEntity createDeviceDetailsEntity(JsonNode deviceData, BrandEntity brand, CategoryEntity categoryEntity, JsonNode moreSpecification) {
        DeviceDetailsEntity deviceDetails = new DeviceDetailsEntity();
        String key = deviceData.path("key").asText();
        DeviceEntity deviceKey = deviceRepository.findByKey(key).get();
        if (deviceKey == null) {
            deviceKey = new DeviceEntity();
            deviceKey.setKey(key);
            deviceKey.setDeviceName(deviceData.path("device_name").asText());
            deviceKey.setDeviceImage(deviceData.path("device_image").asText());
            deviceRepository.save(deviceKey);
        }
        deviceDetails.setDeviceEntityId(deviceKey);
        deviceDetails.setDeviceName(deviceData.path("device_name").asText());
        deviceDetails.setDeviceImage(deviceData.path("device_image").asText());
        deviceDetails.setDisplaySize(deviceData.path("display_size").asText());
        deviceDetails.setDisplayResolution(deviceData.path("display_res").asText());
        deviceDetails.setCamera(deviceData.path("camera").asText());
        deviceDetails.setVideo(deviceData.path("video").asText());
        deviceDetails.setRam(deviceData.path("ram").asText());
        deviceDetails.setChipset(deviceData.path("chipset").asText());
        deviceDetails.setBattery(deviceData.path("battery").asText());
        deviceDetails.setReleaseDate(deviceData.path("release_date").asText());
        deviceDetails.setBody(deviceData.path("body").asText());
        deviceDetails.setOsType(deviceData.path("os_type").asText());
        deviceDetails.setStorage(deviceData.path("storage").asText());
        deviceDetails.setMoreSpecification(String.valueOf(moreSpecification));
        deviceDetails.setBrandId(brand);
        deviceDetails.setCategoryId(categoryEntity);
        deviceDetailsRepository.save(deviceDetails);
        return deviceDetails;
    }

    // For save Model Info:
    private void saveModelInfo(DeviceEntity modelDetails, BrandEntity brand) {
        try {
            ModelEntity modelEntity = this.modelRepository.findByModelName(modelDetails.getDeviceName())
                    .orElseGet(() -> {
                        ModelEntity newModelEntity = new ModelEntity();
                        newModelEntity.setModelName(modelDetails.getDeviceName());
                        newModelEntity.setModelImage(modelDetails.getDeviceImage());
                        newModelEntity.setBrandId(brand);
                        return this.modelRepository.save(newModelEntity);
                    });
        } catch (CustomException e) {
            throw new CustomException("model or battery info Error", e.getHttpStatus());
        }
    }

    // For Save SubData :
//    private void saveSubData(JsonNode dataNode, DeviceDetailsEntity deviceDetails) {
//        if (dataNode.path("title").asText().equalsIgnoreCase("Colors")) {
//            for (JsonNode colorNode : dataNode.path("data")) {
//                // Split the String into individual colors
//                String[] colors = colorNode.asText().split(",");
//                for (String colorName : colors) {
//                    checkColorName(colorName.trim(), deviceDetails);
//                }
//            }
//        } else if (dataNode.path("title").asText().equalsIgnoreCase("Internal")) {
//            for (JsonNode memoryNode : dataNode.path("data")) {
//                System.out.println("memoryNode ===*********=== " + memoryNode.asText());
//                //Split the Array
//                String[] memories = memoryNode.asText().split(", ");
//                System.out.println("memories ===**********=== " + memories.toString());
//                for (String memory : memories) {
//                    String[] specificMemory = memory.split(" ");
//                    System.out.println("specificMemory ===*************=== " + specificMemory.toString());
//                    if (specificMemory.length >= 2) {
//                        checkInternalStorage(specificMemory[0], deviceDetails);
//                        checkRAMStorage(specificMemory[1], deviceDetails);
//                    }
//                }
//            }
//        }
//    }
//
//    // Check Color Name Exists or not:
//    private void checkColorName(String colorName, DeviceDetailsEntity deviceDetails) {
//        ColorEntity color = this.colorRepository.findByColor(colorName)
//                .orElseGet(() -> {
//                    ColorEntity colorEntity = new ColorEntity();
//                    colorEntity.setColor(colorName.trim());
//                    // Save Color:
//                    return this.colorRepository.save(colorEntity);
//                });
//    }
//
//    // Check Internal Storage Exists or not:
//    private void checkInternalStorage(String specificMemory, DeviceDetailsEntity deviceDetails) {
//        System.out.println("specificMemory = " + specificMemory.toString());
//
//        var internalStorage = this.internalStorageRepository.findByInternalStorage(specificMemory.toString())
//                .orElseGet(() -> {
//                    // Create a new Internal Storage:
//                    InternalStorageEntity internalStorageEntity = new InternalStorageEntity();
//                    internalStorageEntity.setInternalStorage(specificMemory.toString());
//                    // Save InternalStorage
//                    return this.internalStorageRepository.save(internalStorageEntity);
//                });
//    }
//
//    // Check Internal Storage Exists or not:
//    private void checkRAMStorage(String specificMemory, DeviceDetailsEntity deviceDetails) {
//        System.out.println("specificMemory = " + specificMemory.toString());
//
//        var ramStorage = this.ramRepository.findByRAMStorage(specificMemory.toString())
//                .orElseGet(() -> {
//                    // Create a new RAM Storage :
//                    RAMStorageEntity ramStorageEntity = new RAMStorageEntity();
//                    ramStorageEntity.setRAMStorage(String.valueOf(specificMemory.toString()));
//                    // Save RAMStorage
//                    return this.ramRepository.save(ramStorageEntity);
//                });
//    }

//    // Check Battery Capacity Exists or not :
//    private void checkModelBattery(DeviceDetailsEntity deviceDetails) {
//        var batteryCapacity = this.batteryRepository.findByBatteryCapacity(deviceDetails.getBattery())
//                .orElseGet(() -> {
//                    //Create a new Battery Capacity:
//                    BatteryEntity batteryEntity = new BatteryEntity();
//                    batteryEntity.setBatteryCapacity(deviceDetails.getBattery());
//                    // Save Model Battery
//                    return this.batteryRepository.save(batteryEntity);
//                });
//        System.out.println("batteryCapacity = " + batteryCapacity);
//    }

}
