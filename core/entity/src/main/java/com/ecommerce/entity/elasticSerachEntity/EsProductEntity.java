package com.ecommerce.entity.elasticSerachEntity;

import com.ecommerce.entity.entity.BaseEntity;
import com.ecommerce.entity.entity.MainCategoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "es_product")
public class EsProductEntity extends BaseEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "es_main_category_id")
    private EsMainCategoryEntity esMainCategoryEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id")
    private EsModelEntity modelEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "color_id")
    private EsColorEntity colorEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "ram_id")
    private EsRamEntity ramEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "internal_storage_id")
    private EsInternalStorageEntity internalStorageEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private EsBrandEntity brandEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "battery_capacity_id")
    private EsBatteryCapacityEntity batteryCapacity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screen_size_id")
    private EsScreenSizeEntity screenSizeEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "sim_slot_id")
    private EsSimSlotEntity simSlotEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "network_id")
    private EsNetworkEntity networkEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "processor_id")
    private EsProcessorEntity processorEntity;

    public EsProductEntity(EsMainCategoryEntity esMaincategoryEntity, EsModelEntity modelEntity, List<EsColorEntity> colorEntityList, List<EsRamEntity> ramEntityList, List<EsInternalStorageEntity> internalStorageList, EsBrandEntity brandEntity, EsSimSlotEntity simSlotEntity, EsBatteryCapacityEntity batteryEntity, EsScreenSizeEntity screenSizeEntity, EsProcessorEntity processorEntity, EsNetworkEntity networkEntity) {
    }

}
