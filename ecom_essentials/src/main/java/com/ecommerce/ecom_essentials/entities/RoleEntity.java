package com.ecommerce.ecom_essentials.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "role_master")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;
}
