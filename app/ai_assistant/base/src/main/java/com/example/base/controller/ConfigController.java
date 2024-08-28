package com.example.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.RespVO;

@RestController
@RequestMapping("/config")
public class ConfigController {
    

    @Value("${age}")
    private int age;

    @GetMapping("/")
    public RespVO<Integer> getAge(){
        return RespVO.success(age);
    }
}
