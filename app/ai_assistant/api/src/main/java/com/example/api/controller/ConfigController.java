//package com.example.api.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.common.RespVO;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping("/config")
//@Slf4j
//public class ConfigController {
//
//    @Value("${name}")
//    private String name;
//
//    @GetMapping
//    public RespVO<String> getConfig(){
//        log.info("获取配置中心的设置");
//        return RespVO.success(name);
//    }
//}
