package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageRoleEnum {

    CUSTOMER(1,"客户"),
    SEAT(2,"坐席"),
    ;
    private final int code;
    private final String message;
}
