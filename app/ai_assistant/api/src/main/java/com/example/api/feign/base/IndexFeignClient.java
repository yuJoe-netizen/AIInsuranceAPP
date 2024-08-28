package com.example.api.feign.base;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.common.RespVO;

@FeignClient("myprefix-Base")
public interface IndexFeignClient {

    @GetMapping("/index")
    public RespVO<String> index();
}
