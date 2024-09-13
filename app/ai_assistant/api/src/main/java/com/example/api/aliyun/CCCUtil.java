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
        log.info("=========获取接入点========================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    /**
     * 获取用户的技能等级列表
     *
     * @param request 请求参数的JSON字符串，包含实例ID、页码和页面大小等信息
     * @return 返回技能等级列表的响应体
     * @throws ExecutionException 执行过程中发生的异常
     * @throws InterruptedException 中断异常
     *
     * 此方法解析请求字符串为一个包含必要参数的Map对象，然后构建一个ListSkillLevelsOfUserRequest对象，
     * 通过调用API获取技能等级列表。最终将获取到的响应对象记录日志，并返回响应体。
     */
    public ListSkillLevelsOfUserResponseBody listSkillLevelsOfUser(String request) throws ExecutionException, InterruptedException {

        // 将请求字符串解析为Map对象，便于获取后续所需参数
        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);

        // 根据解析得到的参数设置API请求的参数
        ListSkillLevelsOfUserRequest getTurnServerListRequest = ListSkillLevelsOfUserRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .pageNumber(Integer.parseInt(requestMap.get("PageNumber")))
                .pageSize(Integer.parseInt(requestMap.get("PageSize")))
                .build();

        // 异步调用API获取技能等级列表
        CompletableFuture<ListSkillLevelsOfUserResponse> response = getClient().listSkillLevelsOfUser(getTurnServerListRequest);

        // 等待API调用结果
        ListSkillLevelsOfUserResponse resp = response.get();

        // 记录技能组信息日志
        log.info("=========技能组========================");
        log.info(JSONUtil.toJsonPrettyStr(resp));

        // 返回技能等级列表的响应体
        return resp.getBody();
    }

    /**
     * Retrieves login details based on a given request.
     *
     * @param request A JSON string containing the request parameters, expected to include instanceId.
     * @return An instance of GetLoginDetailsResponseBody containing the login details.
     * @throws ExecutionException If an error occurs during the execution of the asynchronous method.
     * @throws InterruptedException If an interrupt occurs while waiting for the results of the asynchronous execution.
     */
    public GetLoginDetailsResponseBody getLoginDetails(String request) throws ExecutionException, InterruptedException {

        // Convert the request JSON string into a map object for easy parameter acquisition.
        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);

        // Set up parameters for the API request.
        GetLoginDetailsRequest getLoginDetailsRequest = GetLoginDetailsRequest.builder()
//                .instanceId(requestMap.get("instanceId"))
                .instanceId("aiHelpAgent")
//                .userId("aliyun6663052202@aiHelpAgent")
                .build();

        // Asynchronously send the login details request and get the response.
        CompletableFuture<GetLoginDetailsResponse> response = getClient().getLoginDetails(getLoginDetailsRequest);
        // Wait for the asynchronous operation to complete and get the result.
        GetLoginDetailsResponse resp = response.get();
        // Log the response information for debugging purposes.
        log.info("===================获取登录详情==========================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        // Return the body of the response.
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
        log.info("=================坐席的外呼号码===================");
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
        log.info("=================坐席的权限列表===================");
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
        log.info("=================给服务端上报错误日志===================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public GetUserResponseBody getUser(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        GetUserRequest getLoginDetailsRequest = GetUserRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<GetUserResponse> response = getClient().getUser(getLoginDetailsRequest);
        GetUserResponse resp = response.get();
        log.info("=================获取坐席的信息=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ListConfigItemsResponseBody listConfigItems(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ListConfigItemsRequest getLoginDetailsRequest = ListConfigItemsRequest.builder()
                .objectId(requestMap.get("objectId"))
                .objectType(requestMap.get("objectType"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<ListConfigItemsResponse> response = getClient().listConfigItems(getLoginDetailsRequest);
        ListConfigItemsResponse resp = response.get();
        log.info("=================获取坐席工作台配置信息=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public GetTurnCredentialsResponseBody getTurnCredentials(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        GetTurnCredentialsRequest getLoginDetailsRequest = GetTurnCredentialsRequest.builder()
                .userId(requestMap.get("userId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<GetTurnCredentialsResponse> response = getClient().getTurnCredentials(getLoginDetailsRequest);
        GetTurnCredentialsResponse resp = response.get();
        log.info("=================获取Turn服务的账号密码=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ListDevicesResponseBody listDevices(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ListDevicesRequest getLoginDetailsRequest = ListDevicesRequest.builder()
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<ListDevicesResponse> response = getClient().listDevices(getLoginDetailsRequest);
        ListDevicesResponse resp = response.get();
        log.info("=================获取登录的设备信息=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ResetAgentStateResponseBody resetAgentState(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ResetAgentStateRequest getLoginDetailsRequest = ResetAgentStateRequest.builder()
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<ResetAgentStateResponse> response = getClient().resetAgentState(getLoginDetailsRequest);
        ResetAgentStateResponse resp = response.get();
        log.info("=================重置坐席状态=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public SignInGroupResponseBody signInGroup(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        SignInGroupRequest getLoginDetailsRequest = SignInGroupRequest.builder()
                .signedSkillGroupIdList(requestMap.get("signedSkillGroupIdList"))
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<SignInGroupResponse> response = getClient().signInGroup(getLoginDetailsRequest);
        SignInGroupResponse resp = response.get();
        log.info("=================技能组登录=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public PollUserStatusResponseBody pollUserStatus(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        PollUserStatusRequest getLoginDetailsRequest = PollUserStatusRequest.builder()
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<PollUserStatusResponse> response = getClient().pollUserStatus(getLoginDetailsRequest);
        PollUserStatusResponse resp = response.get();
//        log.info("=================技能组登录=================");
//        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ReadyForServiceResponseBody readyForService(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ReadyForServiceRequest getLoginDetailsRequest = ReadyForServiceRequest.builder()
                .outboundScenario(Boolean.parseBoolean(requestMap.get("outboundScenario")))
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<ReadyForServiceResponse> response = getClient().readyForService(getLoginDetailsRequest);
        ReadyForServiceResponse resp = response.get();
        log.info("=================置空闲=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public TakeBreakResponseBody takeBreak(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        TakeBreakRequest getLoginDetailsRequest = TakeBreakRequest.builder()
                .code(requestMap.get("code"))
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<TakeBreakResponse> response = getClient().takeBreak(getLoginDetailsRequest);
        TakeBreakResponse resp = response.get();
        log.info("=================小休=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public SignOutGroupResponseBody signOutGroup(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        SignOutGroupRequest getLoginDetailsRequest = SignOutGroupRequest.builder()
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<SignOutGroupResponse> response = getClient().signOutGroup(getLoginDetailsRequest);
        SignOutGroupResponse resp = response.get();
        log.info("=================登出=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public PickOutboundNumbersResponseBody pickOutboundNumbers(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        PickOutboundNumbersRequest getLoginDetailsRequest = PickOutboundNumbersRequest.builder()
                .calledNumber(requestMap.get("CalledNumber"))
                .count(Integer.parseInt(requestMap.get("Count")))
                .skillGroupIdList(requestMap.get("SkillGroupIdList"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<PickOutboundNumbersResponse> response = getClient().pickOutboundNumbers(getLoginDetailsRequest);
        PickOutboundNumbersResponse resp = response.get();
        log.info("=================自动选择外呼号码=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public MakeCallResponseBody makeCall(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        MakeCallRequest makeCallRequest = MakeCallRequest.builder()
                .callee(requestMap.get("callee"))
                .caller(requestMap.get("caller"))
                .mediaType(requestMap.get("mediaType"))
                .deviceId(requestMap.get("deviceId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<MakeCallResponse> response = getClient().makeCall(makeCallRequest);
        MakeCallResponse resp = response.get();
        log.info("=================拨打电话=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public GetNumberLocationResponseBody getNumberLocation(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        GetNumberLocationRequest makeCallRequest = GetNumberLocationRequest.builder()
                .number(requestMap.get("Number"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<GetNumberLocationResponse> response = getClient().getNumberLocation(makeCallRequest);
        GetNumberLocationResponse resp = response.get();
        log.info("=================查询号码归属地=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }

    public SaveWebRtcInfoResponseBody saveWebRtcInfo(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        SaveWebRtcInfoRequest makeCallRequest = SaveWebRtcInfoRequest.builder()
                .callId(requestMap.get("callId"))
                .content(requestMap.get("content"))
                .contentType(requestMap.get("contentType"))
                .jobId(requestMap.get("jobId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<SaveWebRtcInfoResponse> response = getClient().saveWebRtcInfo(makeCallRequest);
        SaveWebRtcInfoResponse resp = response.get();
        log.info("=================拨打电话=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }
    public ReleaseCallResponseBody releaseCall(String request) throws ExecutionException, InterruptedException {

        Map<String,String> requestMap = JSONUtil.toBean(request, new TypeReference<Map<String,String>>() {},true);
        // Parameter settings for API request
        ReleaseCallRequest makeCallRequest = ReleaseCallRequest.builder()
                .deviceId(requestMap.get("deviceId"))
                .channelId(requestMap.get("channelId"))
                .jobId(requestMap.get("jobId"))
                .instanceId(requestMap.get("instanceId"))
                .build();
        CompletableFuture<ReleaseCallResponse> response = getClient().releaseCall(makeCallRequest);
        ReleaseCallResponse resp = response.get();
        log.info("=================挂电话=================");
        log.info(JSONUtil.toJsonPrettyStr(resp));
        return resp.getBody();
    }



}
