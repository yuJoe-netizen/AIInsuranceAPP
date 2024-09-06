package com.example.api.aliyun.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;
@Configuration
@PropertySource(value = "classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun.config.ccc")
@Data
public class CCCProperties implements Serializable {
    private String regionId;
    private String endPoint;
    private String instanceId;
}