package com.job.females.in.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {

    USER_NOT_FOUND("User not found", "USER_NOT_FOUND"),
    ROLE_NOT_FOUND("Role not found", "ROLE_NOT_FOUND"),
    USER_ALREADY_EXIST("User Already Exist", "USER_ALREADY_EXIST"),
    ACCOUNT_LOCKED("Your account is been locked, Please contact support", "ACCOUNT_LOCKED"),
    ACCOUNT_DISABLED("Your account is been deactivated, Please contact support ", "ACCOUNT_DISABLED"),
    USER_ROLE_NOT_FOUND("UserRole not found", "USER_ROLE_NOT_FOUND"),
    ERROR_WHILE_REGISTERING_USER("Error while registering user", "ERROR_WHILE_REGISTERING_USER"),
    PASSWORD_NOT_MATCH("Password not match", "PASSWORD_NOT_MATCH"),
    INCORRECT_USERNAME_OR_PASSWORD("Incorrect Username or Password", "INCORRECT_USERNAME_OR_PASSWORD"),
    ERROR_WHILE_CREATING_TOKEN("Error While Creating Token", "ERROR_WHILE_CREATING_TOKEN"),
    INVALID_RESET_PASSWORD_LINK("Invalid reset password link", "INVALID_RESET_PASSWORD_LINK"),
    INVALID_TOKEN("Invalid token", "INVALID_TOKEN");

    private final String value;
    private final String message;

}
