package com.ecommerce.entity.responseDto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTokenDTO {
  private String token;
  private String userRole;
  private Long userId;
  private String userName;

}
