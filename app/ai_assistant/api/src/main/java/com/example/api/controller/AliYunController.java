package com.example.api.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aliyun/ccc")
public class AliYunController {



    @GetMapping("/api")
    public Object api() {

    }
}
