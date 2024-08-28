package com.example.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.RespVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {
    

    @GetMapping
    public RespVO<String> index(){
        log.info("index");
        return RespVO.success("index");
    }
}
