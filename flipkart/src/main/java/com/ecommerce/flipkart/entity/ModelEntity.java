package com.ecommerce.flipkart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "model")
public class ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model_name")
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntityId;
}
