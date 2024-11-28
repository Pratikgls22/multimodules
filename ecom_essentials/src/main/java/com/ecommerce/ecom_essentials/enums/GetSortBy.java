package com.ecommerce.ecom_essentials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GetSortBy {

    ID("id"),
    USER_NAME("userName"),
    EMAIL("email"),
    ADDRESS("address"),
    ROLE("roleName");

    private final String value;
}
