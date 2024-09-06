package com.ecommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass

public class Auditable {

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "created_date",nullable = false)
    private LocalDate createdDate;

    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDate lastModifiedDate;

    @ColumnDefault(value = "false")
    @Column(name = "is_delete",nullable = false)
    private Boolean isDeleted = false;

    @ColumnDefault(value = "true")
    @Column(name = "is_active",nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @CreatedBy
    private UserEntity createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @LastModifiedBy
    private UserEntity updatedBy;

}
