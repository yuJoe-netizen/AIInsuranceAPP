package com.example.api.controller;

import com.example.api.config.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.feign.base.IndexFeignClient;
import com.example.common.RespVO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    @Autowired
    private IndexFeignClient indexFeignClient;

    @Autowired
    WebSocketUtil webSocketUtil;

    @GetMapping("/")
    public RespVO<String> index() {
        log.info("调用API");
        try {
            webSocketUtil.sendMessageTo("1111","yujiangjun");
        } catch (IOException e) {
            log.error("",e);
        }
        return RespVO.success("ok");
    }
}
