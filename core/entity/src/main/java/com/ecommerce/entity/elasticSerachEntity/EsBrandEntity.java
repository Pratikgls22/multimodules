package com.ecommerce.entity.elasticSerachEntity;

import com.ecommerce.entity.entity.BaseEntity;
import com.ecommerce.entity.entity.MainCategoryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "es_brand")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsBrandEntity extends BaseEntity {
    @Id
    private Long id;

    private String  brandName;

    @ManyToOne
    @JoinColumn(name = "es_main_category_id")
    private EsMainCategoryEntity esMainCategoryEntity;
}
