package com.ecommerce.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ResponseTokenDto {
    private String token;
    private String userRole;
    private Long userId;
}
