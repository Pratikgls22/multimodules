package com.ecommerce.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthRequest {
    private String useremail;
    private String password;
}
