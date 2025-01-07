package com.ecommerce.repository.elasticSearchRepository;

import com.ecommerce.entity.elasticSerachEntity.EsProductEntity;
import com.ecommerce.entity.requestDto.esRequestDto.EsExcelRequestDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsProductRepository extends ElasticsearchRepository<EsProductEntity, Long> {

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"esMainCategoryEntity\": \"?#{#esExcelRequestDto.esMainCategoryEntity}\"}}, " +
            "{\"match\": {\"modelEntity\": \"?#{#esExcelRequestDto.esModelEntity}\"}}, " +
            "{\"match\": {\"colorEntity\": \"?#{#esExcelRequestDto.esColorEntity}\"}}, " +
            "{\"match\": {\"ramEntity\": \"?#{#esExcelRequestDto.esRamEntity}\"}}, " +
            "{\"match\": {\"internalStorageEntity\": \"?#{#esExcelRequestDto.esInternalStorageEntity}\"}}, " +
            "{\"match\": {\"brandEntity\": \"?#{#esExcelRequestDto.esBrandEntity}\"}}, " +
            "{\"match\": {\"batteryCapacity\": \"?#{#esExcelRequestDto.esBatteryCapacityEntity}\"}}, " +
            "{\"match\": {\"screenSizeEntity\": \"?#{#esExcelRequestDto.esScreenSizeEntity}\"}}, " +
            "{\"match\": {\"simSlotEntity\": \"?#{#esExcelRequestDto.esSimSlotEntity}\"}}, " +
            "{\"match\": {\"networkEntity\": \"?#{#esExcelRequestDto.esNetworkEntity}\"}}, " +
            "{\"match\": {\"processorEntity\": \"?#{#esExcelRequestDto.esProcessorEntity}\"}}]}}")
    Optional<EsProductEntity> findByAttributes(EsExcelRequestDto esExcelRequestDto);
}
