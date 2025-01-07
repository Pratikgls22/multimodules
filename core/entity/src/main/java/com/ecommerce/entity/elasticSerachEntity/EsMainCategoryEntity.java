package com.ecommerce.entity.elasticSerachEntity;

import com.ecommerce.entity.entity.BaseEntity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "es_main_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsMainCategoryEntity extends BaseEntity {
    @Id
    private Long id;
    private String categoryName;
}
