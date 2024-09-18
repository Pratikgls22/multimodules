package com.ecommerce.entity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "processor", indexes = {
        @Index(name = "index_processor",columnList = "processor_name",unique = true),
})
public class ProcessorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "processor_name")
    private String processorName;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategoryEntity mainCategoryEntity;
}
