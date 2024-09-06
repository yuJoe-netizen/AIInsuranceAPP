package com.example.api.controller;


import com.example.api.aliyun.CCCUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/aliyun/ccc")
@AllArgsConstructor
public class AliYunController {
    private final CCCUtil cccUtil;


    @GetMapping("/api")
    public Object api() throws ExecutionException, InterruptedException {
        return cccUtil.getTurnServerList();
    }
}
