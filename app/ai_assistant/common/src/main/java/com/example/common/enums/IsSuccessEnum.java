package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IsSuccessEnum {
    SUCCESS("1","成功"),
    FAILURE("0","失败");
    private final String code;
    private final String msg;
}
