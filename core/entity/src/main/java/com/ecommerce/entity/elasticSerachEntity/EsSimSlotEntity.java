package com.ecommerce.entity.elasticSerachEntity;

import com.ecommerce.entity.entity.BaseEntity;
import com.ecommerce.entity.entity.MainCategoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "es_sim_slot")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsSimSlotEntity extends BaseEntity {
    @Id
    private Long id;
    private String simSlotType;

    @ManyToOne
    @JoinColumn(name = "es_main_category_id")
    private EsMainCategoryEntity esMainCategoryEntity;
}
