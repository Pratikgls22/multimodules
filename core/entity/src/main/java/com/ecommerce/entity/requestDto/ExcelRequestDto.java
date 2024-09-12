package com.ecommerce.entity.requestDto;

import com.ecommerce.entity.entity.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExcelRequestDto {

    private CategoryEntity categoryEntity;
    private CategoryEntity subCategoryEntity;
    private ModelEntity modelEntity;
    private ColorEntity colorEntity;
    private RamEntity ramEntity;
    private InternalStorageEntity internalStorageEntity;
    private PriceEntity priceEntity;
}
