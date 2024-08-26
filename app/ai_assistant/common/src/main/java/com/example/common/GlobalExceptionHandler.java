package com.example.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public RespVO<Void> handleException(Exception exception){
        log.error("发生了异常", exception);
        return RespVO.failwithMsg(exception.getMessage());
    }
}
