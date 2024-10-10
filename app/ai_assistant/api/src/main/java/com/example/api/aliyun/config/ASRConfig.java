package com.example.api.aliyun.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ASRConfig {

    @Bean
    public IAcsClient acsClient(@Autowired BaseConfig baseConfig) throws ClientException {

        final String accessKeyId = baseConfig.getAccessKeyId();
        final String accessKeySecret = baseConfig.getAccessKeySecret();

        /*
         * 地域ID
         */
        final String regionId = "cn-shanghai";
        final String endpointName = "cn-shanghai";
        final String product = "nls-filetrans";
        final String domain = "filetrans.cn-shanghai.aliyuncs.com";

        // 设置endpoint
        DefaultProfile.addEndpoint(endpointName, regionId, product, domain);

        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);

        return new DefaultAcsClient(profile);
    }

}
