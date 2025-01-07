package com.ecommerce.entity.requestDto.esRequestDto;

import com.ecommerce.entity.elasticSerachEntity.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsExcelRequestDto {

    private EsMainCategoryEntity esMainCategoryEntity;
    private EsModelEntity esModelEntity;
    private EsColorEntity esColorEntity;
    private EsRamEntity esRamEntity;
    private EsInternalStorageEntity esInternalStorageEntity;
    private EsBrandEntity esBrandEntity;
    private EsSimSlotEntity esSimSlotEntity;
    private EsBatteryCapacityEntity esBatteryCapacityEntity;
    private EsScreenSizeEntity esScreenSizeEntity;
    private EsProcessorEntity esProcessorEntity;
    private EsNetworkEntity esNetworkEntity;
}
