package com.job.females.in.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ControllerEnums {

    GET_TOKEN_SUCCESSFULLY("Logged in successfully", "GET_TOKEN_SUCCESSFULLY"),
    RESET_PASSWORD_LINK_SENT_SUCCESSFULLY("Reset password link sent successfully", "RESET_PASSWORD_LINK_SENT_SUCCESSFULLY"),
    USER_ADDED_SUCCESSFULLY("User added successfully", "USER_ADDED_SUCCESSFULLY"),
    EMPLOYER_ADDED_SUCCESSFULLY("Employer added successfully", "EMPLOYER_ADDED_SUCCESSFULLY"),
    TOKEN_CREATED("Token Created Successfully", "TOKEN_CREATED");

    private final String value;
    private final String message;
}
