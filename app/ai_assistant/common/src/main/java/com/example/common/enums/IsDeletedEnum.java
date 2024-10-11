package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IsDeletedEnum {
    YES("0","未删除"),
    NO("1","已删除");
    private final String code;
    private final String msg;
}
