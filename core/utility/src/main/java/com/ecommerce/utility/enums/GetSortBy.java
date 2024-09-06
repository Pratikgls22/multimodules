package com.ecommerce.utility.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GetSortBy {

  ID("id"),
  FIRST_NAME("firstName"),
  LAST_NAME("last_name"),
  USER_NAME("user_name"),
  EMAIL("email");

  private final String value;


}
