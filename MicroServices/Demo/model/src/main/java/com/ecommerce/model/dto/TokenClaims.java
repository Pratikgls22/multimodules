package com.ecommerce.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenClaims {

    private String token;
    private String userName;
    private Long userId;
}
