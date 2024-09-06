package com.ecommerce.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class UserRequestDto {
    @NotEmpty(message = "UserName is Required")
    @Size(max = 255, message = "UserName should not be greater then 255 characters")
    private String userName;

    @NotEmpty(message = "UserEmail is Required")
    @Size(max = 255, message = "Email should not Be Greater then 255 Characters")
    @Pattern(regexp = "/^[a-zA-Z0-9. _%+-]+@[a-zA-Z0-9. -]+\\. [a-zA-Z]{2,}$/",message = "Email is not Valid")
    private String userEmail;

    @NotEmpty(message = "UserPassword is Required")
    @Size(min = 4, message = "Password should not Be less then 8 Characters")
    @Size(max = 8, message = "Password should not Be Greater then 15 Characters")
    private String userPassword;

    private Integer roleId;
}
