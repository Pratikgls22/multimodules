package com.ecommerce.entity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity {

  @CreatedDate
  @Temporal(TemporalType.DATE)
  @Column(name = "created_Date")
  private LocalDate createDate;

  @LastModifiedDate
  @Temporal(TemporalType.DATE)
  @Column(name = "last_modified_date")
  private LocalDate lastModifiedDate;


  @Column(name = "is_active", nullable = false)
  @ColumnDefault("true")
  private boolean isActive = Boolean.TRUE;

  @Column(name = "is_delete", nullable = false)
  @ColumnDefault("false")
  private boolean isDelete = Boolean.FALSE;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", referencedColumnName = "id")
  private UserEntity createdBy;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "updated_by", referencedColumnName = "id")
  private UserEntity updatedBy;
}
