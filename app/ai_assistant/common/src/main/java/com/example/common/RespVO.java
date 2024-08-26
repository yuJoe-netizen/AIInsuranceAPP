package com.example.common;

import lombok.Data;

@Data
public class RespVO<T> {
    private int code;
    private String msg;
    private T data;

    public static int SUCCESS_CODE = 200;
    public static String SUCCESS_MSG = "操作成功";
    public static int FAIL_CODE = 200;
    public static String FAIL_MSG = "操作成功";

    public static <T> RespVO<T> success(T data) {
        RespVO<T> respVO = new RespVO<>();
        respVO.setCode(SUCCESS_CODE);
        respVO.setMsg(SUCCESS_MSG);
        respVO.setData(data);
        return respVO;
    }

    public static <T> RespVO<T> common(int code, String msg, T data) {
        RespVO<T> respVO = new RespVO<>();
        respVO.setCode(code);
        respVO.setMsg(msg);
        respVO.setData(data);
        return respVO;
    }

    public static RespVO<Void> fail() {
        RespVO<Void> respVO = new RespVO<>();
        respVO.setCode(FAIL_CODE);
        respVO.setMsg(FAIL_MSG);
        respVO.setData(null);
        return respVO;
    }

    public static RespVO<Void> failwithMsg(String msg) {
        RespVO<Void> respVO = new RespVO<>();
        respVO.setCode(FAIL_CODE);
        respVO.setMsg(msg);
        respVO.setData(null);
        return respVO;
    }
}
