package com.example.api.aliyun.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun.config.asr")
public class ASRConfigProperties {

    private String appKey;
}
