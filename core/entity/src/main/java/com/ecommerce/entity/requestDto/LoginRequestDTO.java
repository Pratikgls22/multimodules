package com.ecommerce.entity.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequestDTO {
  @Size(max = 255, message = "email should not greater than 255 characters")
  @NotEmpty(message = "email must not be empty")
  @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", message = "email is invalid")
  private String email;

  @NotEmpty(message = "password must not be Empty")
  @Size(min = 6, message = "password must be min 6 character")
  @Size(max = 255, message = "password must not greater than 255 characters")
  @Pattern(regexp = "^(?=[^A-Z]*+)(?=[^a-z]*+)(?=\\D*+)(?=[^#?!@$%^&*-]*+).{6,255}$",
    message = "password must contain one lowercase letter,one uppercase letter,one number,one special character")
  private String password;
}
