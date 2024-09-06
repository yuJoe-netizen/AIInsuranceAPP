package com.example.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.example.api.aliyun.config.BaseConfig;
import com.example.common.RespVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@Slf4j
@AllArgsConstructor
public class ConfigController {
    private final BaseConfig baseConfig;

    @GetMapping
    public RespVO<BaseConfig> getConfig(){
        log.info("获取配置中心的设置");
        BaseConfig properties = BeanUtil.copyProperties(baseConfig, BaseConfig.class);
        log.info("{}", JSONUtil.toJsonPrettyStr(properties));
        return RespVO.success(properties);
    }
}
