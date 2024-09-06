package com.ecommerce.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UpdateUserDto {

    private String userName;
    private Long roleId;
}
