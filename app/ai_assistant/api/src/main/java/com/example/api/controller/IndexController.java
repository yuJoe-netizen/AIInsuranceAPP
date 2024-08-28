package com.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.feign.base.IndexFeignClient;
import com.example.common.RespVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    @Autowired
    private IndexFeignClient indexFeignClient;

    @GetMapping("/")
    public RespVO<String> index() {
        log.info("调用API");
        return indexFeignClient.index();
    }
}
