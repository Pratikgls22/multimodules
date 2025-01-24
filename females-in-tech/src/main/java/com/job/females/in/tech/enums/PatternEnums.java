package com.job.females.in.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PatternEnums {

    ALLOWED_CHARS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"),
    LOWERCASE_LETTERS("abcdefghijklmnopqrstuvwxyz"),
    UPPERCASE_LETTERS("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    DIGITS("0123456789"),
    CURRENT_DATE("yyyy-MM-dd HH:mm:ss"),
    LOCAL_TIME("HH:mm:ss"),
    LOCAL_DATE("yyyy-MM-dd"),
    LOCAL_DATE_WITH_AMPM("hh:mm:ss a"),
    SIMPLE_DATE("yyyy-MM-dd HH:mm:ss.SSS"),
    FORMATED_DATE("yyyy/MM/dd hh:mm a"),


    ;

    private final String value;}