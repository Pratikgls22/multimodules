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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneDataServiceImpl implements PhoneDataService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final ModelDetailsRepository modelDetailsRepository;
    private final SpecificationRepository specificationRepository;
    private final SpecificationDataRepository specificationDataRepository;
    private final ColorRepository colorRepository;
    private final ImageRepository imageRepository;
    private final BatteryRepository batteryRepository;
    private final RamRepository ramRepository;
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
            CategoryEntity categoryEntity = this.categoryRepository.findByCategory(category).orElseThrow(() -> new CustomException(ExceptionEnum.CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

            JsonNode deviceData = objectMapper.readTree(response);
            if (deviceData.path("status").asInt() == 200) {
                for (JsonNode brandNode : deviceData.path("data")) {
                    BrandEntity brand = brandRepository.findByBrandName(brandNode.path("brand_name").asText()).orElseGet(() -> {
                        BrandEntity brandEntity = new BrandEntity();
                        brandEntity.setBrandName(brandNode.path("brand_name").asText());
                        brandEntity.setCategoryId(categoryEntity);
                        return this.brandRepository.save(brandEntity);
                    });

                    // Create a Map to store deviceKey and modelEntity mapping
                    Map<String, ModelEntity> deviceKeyAndModelMap = new HashMap<>();

                    for (JsonNode deviceNode : brandNode.path("device_list")) {
                        String deviceName = deviceNode.path("device_name").asText();
                        String deviceType = deviceNode.path("device_type").asText();
                        String deviceImage = deviceNode.path("device_image").asText();
                        String deviceKey = deviceNode.path("key").asText();

                        ModelEntity modelEntity = this.modelRepository.findByKey(deviceNode.path("key").asText()).orElseGet(() -> {
                            ModelEntity newModelEntity = new ModelEntity();
                            newModelEntity.setDeviceName(deviceName);
                            newModelEntity.setDeviceType(deviceType);
                            newModelEntity.setDeviceImage(deviceImage);
                            newModelEntity.setKey(deviceKey);
                            newModelEntity.setBrandId(brand);
                            newModelEntity.setCategoryId(categoryEntity);
                            return this.modelRepository.save(newModelEntity);
                        });

                        // Add Key In List :
                        deviceKeyAndModelMap.put(modelEntity.getKey(),modelEntity);
                        System.out.println("deviceKeyAndModelMap = " + deviceKeyAndModelMap);
                    }
                    for (String key : deviceKeyAndModelMap.keySet()) {
                        if (!key.isEmpty()) {
                            ModelEntity modelEntity = deviceKeyAndModelMap.get(key);
                            // call Model Details Method :
                            fetchAndSaveModelDetail(key, brand, categoryEntity, modelEntity);
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
        var brandName = modelDetailsRepository.findByBrandId_BrandName(brand);
        if (brandName.isEmpty()) {
            throw new CustomException(ExceptionEnum.BRAND_HAS_NO_DATA.getMessage(), HttpStatus.NO_CONTENT);
        }
        return brandName;
    }

    // Fetch All Models :
    @Override
    public List<String> fetchModelList(String brand) {
        var brandName = modelRepository.findDeviceNamesByBrand(brand);
        if (brandName.isEmpty()){
            throw new CustomException(ExceptionEnum.BRAND_HAS_NO_DATA.getMessage(), HttpStatus.NO_CONTENT);
        }
        return brandName;
    }

    @Override
    public List<String> fetchCameraList(String model) {
        var modelName = this.cameraRepository.findByCameraByModel(model);
        if (modelName.isEmpty()){
            throw new CustomException(ExceptionEnum.MODEL_NAME_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        return modelName;
    }

    @Override
    public List<String> fetchImageList(String model) {
        var modelName = this.imageRepository.findByImageByModel(model);
        if (modelName.isEmpty()){
            throw new CustomException(ExceptionEnum.MODEL_NAME_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        return modelName;
    }

    @Override
    public List<String> fetchInternalStorageList() {
        return this.internalStorageRepository.findByStorage();
    }

    @Override
    public List<String> fetchOperatingSystemList() {
        return this.operatingSystemRepository.findByOS();
    }

    @Override
    public List<String> fetchRamList() {
        return this.ramRepository.findByRam();
    }

    // Fetch All Brand :
    @Override
    public List<String> fetchBrandList() {
        return this.brandRepository.findByBrand();
    }

    // Fetch All Batteries :
    @Override
    public List<String> fetchBatteryList() {
        return this.batteryRepository.findByBattery();
    }

    // Fetch All Batteries :
    @Override
    public List<String> fetchColorList() {
        return this.colorRepository.findByColor();
    }
//
//    @Override
//    public List<String> fetchModelByBrand(String brand) {
//        return this.modelDataRepository.findByBrandId_BrandName(brand);
//    }

    //@Override
    private JsonNode fetchAndSaveModelDetail(String key, BrandEntity brand, CategoryEntity categoryEntity, ModelEntity modelEntity) {
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
                    // Save Model Details :
                    ModelDetailsEntity modelDetails = saveModelDetails(deviceNode, brand, categoryEntity, moreSpecification);

//                    // Save Model Info:
////                    saveModelInfo(deviceNode, deviceDetails);
//                    saveModelInfo(deviceDetails);
//
//
                    // Save Operating System data:
                    saveOperatingSystem(deviceNode, brand, modelEntity);
                    // Save Ram Storage data:
                    saveRamStorage(deviceNode, brand, modelEntity);
                    // Save Internal Storage data:
                    saveInternalStorage(deviceNode, brand, modelEntity);
                    // Save Battery data:
                    saveBattery(deviceNode, brand, modelEntity);
                    // Save Images:
                    saveImages(deviceNode, brand, modelEntity);


                    // For More Specification Data :
                    JsonNode specificationNode = deviceNode.path("more_specification");
                    if (specificationNode.isArray()) {
                        for (JsonNode titleNode : specificationNode) {

                            // Set Title of Specification ;
                            SpecificationEntity specificationEntity = new SpecificationEntity();
                            specificationEntity.setTitle(getTextValue(titleNode, "title"));
                            specificationEntity.setBrandId(brand);
                            specificationEntity.setModelId(modelEntity);

                            // Save Specification :
                            SpecificationEntity specification = this.specificationRepository.save(specificationEntity);
                            System.out.println("specificationEntity ********************* " + specificationEntity.getTitle());


                            List<SpecificationDataEntity> specDataList = new ArrayList<>();

                            for (JsonNode dataNode : titleNode.path("data")) {
                                System.out.println("dataNode.getNodeType() ====== " + dataNode.getNodeType());
                                SpecificationDataEntity specificationDataEntity = new SpecificationDataEntity();
                                specificationDataEntity.setTitle(getTextValue(dataNode, "title"));
                                specificationDataEntity.setSpecificationId(specification);

                                //Collect Data values:
                                //List<String> dataValues = new ArrayList<>();
                                StringBuilder dataValues = new StringBuilder();
                                for (JsonNode valueNode : dataNode.path("data")) {
                                    dataValues.append(valueNode.asText());
                                }
                                specificationDataEntity.setData(dataValues.toString());
                                System.out.println("specificationDataEntity.getTitle() ===== " + specificationDataEntity.getTitle());
                                System.out.println("specificationDataEntity.getData() ===== " + specificationDataEntity.getData());
                                specDataList.add(specificationDataEntity);
                                // Save Specification Data:
                                SpecificationDataEntity specificationData = this.specificationDataRepository.save(specificationDataEntity);

                                saveColor(dataNode, modelDetails);
//
                                //For Camera Info form Specification :
                                cameraInfo(specification, specificationData, modelEntity, brand);
                            }
                        }
                    }
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

    private String getTextValue(JsonNode node, String fieldName) {
        JsonNode valueNode = node.get(fieldName);
        return valueNode != null ? valueNode.asText() : null;
    }

    // For Save Operating System Data :
    private void saveOperatingSystem(JsonNode deviceNode, BrandEntity brand, ModelEntity modelEntity) {
        JsonNode operatingSystemNode = deviceNode.path("os_type");
        var oSystem = this.operatingSystemRepository.findByOperatingSystem(operatingSystemNode.asText())
                .orElseGet(() -> {
                    OperatingSystemEntity operatingSystemEntity = new OperatingSystemEntity();
                    operatingSystemEntity.setOperatingSystem(operatingSystemNode.asText());
                    operatingSystemEntity.setBrandId(brand);
                    operatingSystemEntity.setModelId(modelEntity);
                    return this.operatingSystemRepository.save(operatingSystemEntity);
                });
        System.out.println("oSystem = " + oSystem);
    }

    // For Save Ram Storage:
    private void saveRamStorage(JsonNode deviceNode, BrandEntity brand, ModelEntity modelEntity) {
        JsonNode ramNode = deviceNode.path("ram");
        // Extract only the RAM size (e.g., "6GB") from the string
        String extractedRam = extractRamSize(ramNode.asText());

        if (extractedRam != null && !extractedRam.isEmpty()) {
            var ramStorage = this.ramRepository.findByRamStorage(extractedRam)
                    .orElseGet(() -> {
                        // Create a new RAM Storage entity
                        RamStorageEntity ramStorageEntity = new RamStorageEntity();
                        ramStorageEntity.setRamStorage(extractedRam);
                        ramStorageEntity.setBrandId(brand);
                        ramStorageEntity.setModelId(modelEntity);
                        // Save RAM storage
                        return this.ramRepository.save(ramStorageEntity);
                    });

            System.out.println("ramStorage = " + ramStorage);
        }
    }

    // Helper method to extract RAM size
    private String extractRamSize(String ramText) {
        if (ramText != null) {
            // Use regex to extract the RAM size
            Pattern pattern = Pattern.compile("(\\d+)(MB|GB|TB)"); // Matches patterns like "6GB", "12GB"
            Matcher matcher = pattern.matcher(ramText);
            if (matcher.find()) {
                return matcher.group(); // Return the first match (e.g., "6GB")
            }
        }
        return null; // Return null if no match is found
    }

    // For Save Internal Storage:
    private void saveInternalStorage(JsonNode deviceNode, BrandEntity brand, ModelEntity modelEntity) {
        JsonNode internalNode = deviceNode.path("storage");
        String storageText = internalNode.asText();

        if (storageText != null && !storageText.isEmpty()) {
            // Use regex to extract all storage options (e.g., "128GB", "256GB", "1TB")
            Pattern pattern = Pattern.compile("\\d+(GB|TB|MB)"); // Matches patterns like "128GB", "1TB"
            Matcher matcher = pattern.matcher(storageText);

            while (matcher.find()) { // Iterate over all matches
                String storage = matcher.group(); // Extract the matched storage (e.g., "128GB")

                // Check if this storage already exists in the repository
                var internalStorage = this.internalStorageRepository.findByInternalStorage(storage)
                        .orElseGet(() -> {
                            // Create a new Internal Storage entry
                            InternalStorageEntity internalStorageEntity = new InternalStorageEntity();
                            internalStorageEntity.setInternalStorage(storage); // Save the extracted storage
                            internalStorageEntity.setBrandId(brand);
                            internalStorageEntity.setModelId(modelEntity);
                            // Save to repository
                            return this.internalStorageRepository.save(internalStorageEntity);
                        });

                System.out.println("Saved internalStorage = " + internalStorage);
            }
        }
    }

    // For Save Battery:
    private void saveBattery(JsonNode deviceNode, BrandEntity brand, ModelEntity modelEntity) {
        JsonNode batteryNode = deviceNode.path("battery");
        var battery = this.batteryRepository.findByBatteryCapacity(batteryNode.asText())
                .orElseGet(() -> {
                    // Create a new Battery :
                    BatteryEntity batteryEntity = new BatteryEntity();
                    batteryEntity.setBatteryCapacity(batteryNode.asText());
                    batteryEntity.setBrandId(brand);
                    batteryEntity.setModelId(modelEntity);
                    // Save Battery
                    return this.batteryRepository.save(batteryEntity);
                });
        System.out.println("battery = " + battery);
    }

    // For Save Images:
    private void saveImages(JsonNode deviceNode, BrandEntity brand, ModelEntity modelEntity) {
        JsonNode imageNode = deviceNode.path("device_image");
        var image = this.imageRepository.findByUrl(imageNode.asText())
                .orElseGet(() -> {
                    // Create a new Image Url :
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setUrl(imageNode.asText());
                    imageEntity.setBrandId(brand);
                    imageEntity.setModelId(modelEntity);
                    // Save Battery
                    return this.imageRepository.save(imageEntity);
                });
        System.out.println("image = " + image);
    }


    // For get Camera Info for Specification :
    private void cameraInfo(SpecificationEntity specification, SpecificationDataEntity specificationData, ModelEntity modelEntity, BrandEntity brand) {
        System.out.println("specification.getTitle()" + specification.getTitle());
        if (specification.getTitle().equalsIgnoreCase("Main Camera") || specification.getTitle().equalsIgnoreCase("selfie camera")) {

            if (specificationData.getTitle().equalsIgnoreCase("Triple") ||
                    specificationData.getTitle().equalsIgnoreCase("Dual") ||
                    specificationData.getTitle().equalsIgnoreCase("Single")) {

                CameraEntity cameraEntity = cameraRepository.findByModelId(modelEntity).orElseGet(() -> {
                    CameraEntity newCameraEntity = new CameraEntity();
                    newCameraEntity.setModelId(modelEntity);
                    newCameraEntity.setBrandId(brand);
                    return cameraRepository.save(newCameraEntity);
                });

                if (specification.getTitle().equalsIgnoreCase("Main Camera")) {
                    String simplifiedMainCamera = spiltCameraDetails(specificationData.getData());
                    System.out.println("simplifiedMainCamera /*/*/ = " + simplifiedMainCamera);

                    if(Optional.ofNullable(cameraEntity.getMainCamera()).isEmpty()){
                        cameraEntity.setMainCamera(simplifiedMainCamera);
                    }
                }
                if (specification.getTitle().equalsIgnoreCase("selfie camera")) {
                    String simplifiedSelfieCamera = spiltCameraDetails(specificationData.getData());
                    System.out.println("simplifiedSelfieCamera /*/*/ = " + simplifiedSelfieCamera);

                    if(Optional.ofNullable(cameraEntity.getSelfieCamera()).isEmpty()){
                        cameraEntity.setSelfieCamera(simplifiedSelfieCamera);
                    }
                }
                CameraEntity saveMainCamera = this.cameraRepository.save(cameraEntity);
                System.out.println("saveMainCamera /*/*/*/*/*/*/*/*/*/*// " + saveMainCamera);
            }

        }
    }

    // For Spilt Camera Details:
    private String spiltCameraDetails(String cameraDetails) {
        StringBuilder mpValues = new StringBuilder();

        if (cameraDetails.contains("\n")) {
            String[] cameraDetail = cameraDetails.split("\n");
            for (String cameraResolution : cameraDetail) {
                if (!cameraResolution.trim().isEmpty()) {
                    splitByComma(cameraResolution, mpValues);
                }
            }
        } else {
            splitByComma(cameraDetails, mpValues);
        }
        return mpValues.toString();
    }

    // Split by Comma
    private void splitByComma(String cameraResolution, StringBuilder mpValues) {
        String[] detail = cameraResolution.split(",");
        System.out.println("detail ======================= " + Arrays.toString(detail));
        String resolution = detail[0].trim();
        System.out.println("resolution *********============*********** " + resolution);
        mpValues.append(resolution + " ");
    }


    // Save DeviceDetail Data :
    private ModelDetailsEntity saveModelDetails(JsonNode deviceData, BrandEntity brand, CategoryEntity categoryEntity, JsonNode moreSpecification) {
        var deviceName = modelDetailsRepository.findByDeviceName(deviceData.path("device_name").asText()).orElseGet(() -> createModelDetailsEntity(deviceData, brand, categoryEntity, moreSpecification));
        return deviceName;
    }

    // Create DeviceDetails :
    private ModelDetailsEntity createModelDetailsEntity(JsonNode deviceData, BrandEntity brand, CategoryEntity categoryEntity, JsonNode moreSpecification) {
        ModelDetailsEntity deviceDetails = new ModelDetailsEntity();
        String key = deviceData.path("key").asText();
        ModelEntity deviceKey = modelRepository.findByKey(key).get();
        if (deviceKey == null) {
            deviceKey = new ModelEntity();
            deviceKey.setKey(key);
            deviceKey.setDeviceName(deviceData.path("device_name").asText());
            deviceKey.setDeviceImage(deviceData.path("device_image").asText());
            modelRepository.save(deviceKey);
        }
        deviceDetails.setModelId(deviceKey);
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
        modelDetailsRepository.save(deviceDetails);
        return deviceDetails;
    }

//    // For save Model Info:
//    private void saveModelInfo(ModelEntity modelDetails, BrandEntity brand) {
//        try {
//            ModelDataEntity modelDataEntity = this.modelDataRepository.findByModelName(modelDetails.getDeviceName())
//                    .orElseGet(() -> {
//                        ModelDataEntity newModelDataEntity = new ModelDataEntity();
//                        newModelDataEntity.setModelName(modelDetails.getDeviceName());
//                        newModelDataEntity.setModelImage(modelDetails.getDeviceImage());
//                        newModelDataEntity.setBrandId(brand);
//                        return this.modelDataRepository.save(newModelDataEntity);
//                    });
//        } catch (CustomException e) {
//            throw new CustomException("model or battery info Error", e.getHttpStatus());
//        }
//    }

    // For Save SubData :
    private void saveColor(JsonNode dataNode, ModelDetailsEntity modelDetails) {
        String colorTitle = dataNode.path("title").asText();
        if (colorTitle.equalsIgnoreCase("Colors")) {
            for (JsonNode colorNode : dataNode.path("data")) {
                // Split the String into individual colors
                String[] colors = colorNode.asText().split(",");
                for (String colorName : colors) {
                    checkColorName(colorName.trim(), modelDetails);
                }
            }
        }
    }

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
    // Check Color Name Exists or not:
    private void checkColorName(String colorName, ModelDetailsEntity modelDetails) {
        ColorEntity color = this.colorRepository.findByColor(colorName)
                .orElseGet(() -> {
                    ColorEntity colorEntity = new ColorEntity();
                    colorEntity.setColor(colorName.trim());
                    colorEntity.setBrandId(modelDetails.getBrandId());
                    colorEntity.setModelId(modelDetails.getModelId());
                    // Save Color:
                    return this.colorRepository.save(colorEntity);
                });
    }
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

