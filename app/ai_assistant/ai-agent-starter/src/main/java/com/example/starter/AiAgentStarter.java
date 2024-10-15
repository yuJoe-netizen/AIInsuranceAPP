package com.example.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableFeignClients
@MapperScan(basePackages = "com.example.db.mapper")
public class AiAgentStarter {
    public static void main(String[] args) {
        SpringApplication.run(AiAgentStarter.class, args);
    }
}