package com.example.common.enums;

import lombok.Getter;

@Getter
public enum CallRoleEnum {

    CUSTOMER("1", "客户"),
    AGENT("0", "坐席");

    private final String code;
    private final String desc;

    CallRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CallRoleEnum getByCode(String code) {
        for (CallRoleEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
