package com.ecommerce.entity.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserDTO {

  @NotEmpty(message = "First Name Is Required!!")
  @Size(max = 255, message = "First Name Should Not Be Greater Then 255 Characters")
  private String firstName;

  @NotEmpty(message = "Last Name Is Required!!")
  @Size(max = 255, message = "Last Name Should Not Be Greater Then 255 Characters")
  private String lastName;

  @NotEmpty(message = "Email is Required!!")
  @Size(max = 255, message = "Last Name Should Not Be Greater Then 255 Characters")
  @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", message = "email is invalid")
  private String email;

  @NotEmpty(message = "password must not be Empty")
  @Size(min = 6, message = "password must be min 6 character")
  @Size(max = 255, message = "password must not greater than 255 characters")
  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,255}$",
    message = "password must contain one lowercase letter,one uppercase letter,one number,one special character")
  private String password;

  private String userName;

  private LocalDate dob;
  private String gender;
  private String address;

  private Integer rolesId;

}
