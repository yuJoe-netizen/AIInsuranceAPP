package com.example.api.aliyun;

import cn.hutool.json.JSONUtil;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.ccc20200701.AsyncClient;
import com.aliyun.sdk.service.ccc20200701.models.*;
import com.example.api.aliyun.config.BaseConfig;
import com.example.api.aliyun.config.CCCProperties;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class CCCUtil {

    private final BaseConfig baseConfig;

    private AsyncClient getClient() {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(baseConfig.getAccessKeyId())
                .accessKeySecret(baseConfig.getAccessKeySecret())
                //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                .build());
        CCCProperties ccc = baseConfig.getCcc();
        // Configure the Client
        // Region ID
        // Endpoint 请参考 https://api.aliyun.com/product/CCC
        return AsyncClient.builder()
                .region(ccc.getRegionId()) // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/CCC
                                .setEndpointOverride(ccc.getEndPoint())
                ).build();
    }

    /**
     * @return 获取接入点列表
     */
    public GetTurnServerListResponseBody getTurnServerList() throws ExecutionException, InterruptedException {


        // Parameter settings for API request
        GetTurnServerListRequest getTurnServerListRequest = GetTurnServerListRequest.builder()
                .instanceId(baseConfig.getCcc().getInstanceId())
                .build();

        CompletableFuture<GetTurnServerListResponse> response = getClient().getTurnServerList(getTurnServerListRequest);
        GetTurnServerListResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public ListSkillLevelsOfUserResponseBody listSkillLevelsOfUser() throws ExecutionException, InterruptedException {


        // Parameter settings for API request
        ListSkillLevelsOfUserRequest getTurnServerListRequest = ListSkillLevelsOfUserRequest.builder()
                .instanceId(baseConfig.getCcc().getInstanceId())
                .build();

        CompletableFuture<ListSkillLevelsOfUserResponse> response = getClient().listSkillLevelsOfUser(getTurnServerListRequest);
        ListSkillLevelsOfUserResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
}
