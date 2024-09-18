package com.ecommerce.entity.requestDto;

import com.ecommerce.entity.entity.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelRequestDto {

    private MainCategoryEntity mainCategoryEntity;
    private ModelEntity modelEntity;
    private ColorEntity colorEntity;
    private RamEntity ramEntity;
    private InternalStorageEntity internalStorageEntity;
    private BrandEntity brandEntity;
    private SimSlotEntity simSlotEntity;
    private BatteryCapacityEntity batteryCapacityEntity;
    private ScreenSizeEntity screenSizeEntity;
    private ProcessorEntity processorEntity;
    private NetworkEntity networkEntity;
}
