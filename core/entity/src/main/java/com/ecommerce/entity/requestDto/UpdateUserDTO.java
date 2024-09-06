package com.ecommerce.entity.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateUserDTO {

  @NotEmpty(message = "First Name Is Required!!")
  @Size(max = 255, message = "First Name Should Not Be Greater Then 255 Characters")
  private String firstName;

  @NotEmpty(message = "Last Name Is Required!!")
  @Size(max = 255, message = "Last Name Should Not Be Greater Then 255 Characters")
  private String lastName;

  private String userName;

  private LocalDate dob;
  private String gender;
  private String address;

  private Long rolesId;

}
