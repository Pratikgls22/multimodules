package com.ecommerce.utility.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {

    UNAUTHORIZED("Unauthorized", "UNAUTHORIZED"),
    SOMETHING_WENT_WRONG("Something went wrong", "SOMETHING_WENT_WRONG"),
    USER_NAME_NOT_FOUND("UserName not found", "USER_NAME_NOT_FOUND"),
    USER_NOT_FOUND("User not found", "USER_NOT_FOUND"),
    USER_DETAILS_NOT_FOUND("User details  not found", "USER_DETAILS_NOT_FOUND"),
    USER_ROLE_NOT_FOUND("User role not found", "USER_ROLE_NOT_FOUND"),
    INCORRECT_USERNAME_OR_PASSWORD("Incorrect username or password", "INCORRECT_USERNAME_OR_PASSWORD"),
    INVALID_CREDENTIALS("Invalid credentials", "INVALID_CREDENTIALS"),
    INVALID_PASSWORD("Invalid password", "INVALID_PASSWORD"),
    USER_EXISTS("User already exists", "USER_EXISTS"),
    USER_ALREADY_EXIST_WITH_THIS_EMAIL("User already exist with this email", "USER_ALREADY_EXIST_WITH_THIS_EMAIL"),
    INVALID_TOKEN("Invalid token", "INVALID_TOKEN"),
    ROLE_NOT_FOUND("Role not found", "ROLE_NOT_FOUND"),
    ROLE_ALREADY_EXIST("Role already exists", "ROLE_EXISTS"),

    ;

    private final String value;
    private final String message;
}

