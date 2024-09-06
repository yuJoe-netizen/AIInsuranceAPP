package com.example.api.aliyun;

import cn.hutool.core.lang.TypeReference;
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

import java.util.Map;
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
    public GetTurnServerListResponseBody getTurnServerList(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        GetTurnServerListRequest getTurnServerListRequest = GetTurnServerListRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .build();

        CompletableFuture<GetTurnServerListResponse> response = getClient().getTurnServerList(getTurnServerListRequest);
        GetTurnServerListResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public ListSkillLevelsOfUserResponseBody listSkillLevelsOfUser(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ListSkillLevelsOfUserRequest getTurnServerListRequest = ListSkillLevelsOfUserRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .pageNumber(Integer.parseInt(requestMap.get("PageNumber")))
                .pageSize(Integer.parseInt(requestMap.get("PageSize")))
                .build();

        CompletableFuture<ListSkillLevelsOfUserResponse> response = getClient().listSkillLevelsOfUser(getTurnServerListRequest);
        ListSkillLevelsOfUserResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public GetLoginDetailsResponseBody getLoginDetails(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);

        // Parameter settings for API request
        GetLoginDetailsRequest getLoginDetailsRequest = GetLoginDetailsRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .build();

        CompletableFuture<GetLoginDetailsResponse> response = getClient().getLoginDetails(getLoginDetailsRequest);
        GetLoginDetailsResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ListOutboundNumbersOfUserResponseBody listOutboundNumbersOfUser(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);

        // Parameter settings for API request
        ListOutboundNumbersOfUserRequest getLoginDetailsRequest = ListOutboundNumbersOfUserRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .pageNumber(Integer.parseInt(requestMap.get("PageNumber")))
                .pageSize(Integer.parseInt(requestMap.get("PageSize")))
                .build();

        CompletableFuture<ListOutboundNumbersOfUserResponse> response = getClient().listOutboundNumbersOfUser(getLoginDetailsRequest);
        ListOutboundNumbersOfUserResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ListPrivilegesOfUserResponseBody listPrivilegesOfUser(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ListPrivilegesOfUserRequest getLoginDetailsRequest = ListPrivilegesOfUserRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<ListPrivilegesOfUserResponse> response = getClient().listPrivilegesOfUser(getLoginDetailsRequest);
        ListPrivilegesOfUserResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public SaveTerminalLogResponseBody saveTerminalLog(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        SaveTerminalLogRequest getLoginDetailsRequest = SaveTerminalLogRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .appName(requestMap.get("appName"))
                .dataType(Integer.parseInt(requestMap.get("dataType")))
                .methodName(requestMap.get("methodName"))
                .callId(requestMap.get("callId"))
                .content(requestMap.get("content"))
                .jobId(requestMap.get("jobId"))
                .status(requestMap.get("status"))
                .uniqueRequestId(requestMap.get("uniqueRequestId"))
                .build();
        CompletableFuture<SaveTerminalLogResponse> response = getClient().saveTerminalLog(getLoginDetailsRequest);
        SaveTerminalLogResponse resp = response.get();
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
}
