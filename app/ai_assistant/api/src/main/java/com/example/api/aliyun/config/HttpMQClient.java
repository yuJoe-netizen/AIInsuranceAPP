package com.example.api.aliyun.config;

import cn.hutool.core.util.StrUtil;
import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpMQClient {


    @Bean
    public MQConsumer mqConsumer(MQClientProperties properties, BaseConfig baseConfig) {
        MQClient mqClient = new MQClient(
                // 设置HTTP协议客户端接入点，进入消息队列RocketMQ版控制台实例详情页面的接入点区域查看。
                properties.getEndpoint(),
                // 请确保环境变量ALIBABA_CLOUD_ACCESS_KEY_ID、ALIBABA_CLOUD_ACCESS_KEY_SECRET已设置。
                // AccessKey ID，阿里云身份验证标识。
                baseConfig.getAccessKeyId(),
                // AccessKey Secret，阿里云身份验证密钥。
                baseConfig.getAccessKeySecret()
        );

        final MQConsumer consumer;
        if (StrUtil.isNotEmpty(properties.getInstanceId())) {
            consumer = mqClient.getConsumer(properties.getInstanceId(), properties.getTopic(), properties.getGroupId(), null);
        } else {
            consumer = mqClient.getConsumer(properties.getTopic(), properties.getGroupId());
        }
        return consumer;
    }
}
