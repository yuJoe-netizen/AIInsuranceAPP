package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {
    YES("1","未删除"),
    NO("0","已删除");
    private final String code;
    private final String msg;
}
