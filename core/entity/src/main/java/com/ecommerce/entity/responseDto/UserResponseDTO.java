package com.ecommerce.entity.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponseDTO {
  private Long userId;
  private String userName;
  private String email;
  private String firstName;
  private String lastName;
  private Boolean isActive;
  private LocalDate dateOfBirth;
  private String gender;
  private String address;
}
