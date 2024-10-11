package com.example.api.aliyun.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.example.api.aliyun.config.ASRConfigProperties;
import com.example.api.aliyun.config.BaseConfig;
import com.example.api.aliyun.vo.ASRResponse;
import com.example.common.enums.IsSuccessEnum;
import com.example.common.util.SpringContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云ASR工具类
 */
@Slf4j
public class ASRUtil {

    public static final String REGIONID = "cn-shanghai";
    public static final String ENDPOINTNAME = "cn-shanghai";
    public static final String PRODUCT = "nls-filetrans";
    public static final String DOMAIN = "filetrans.cn-shanghai.aliyuncs.com";
    public static final String API_VERSION = "2018-08-17";  // 中国站版本
    // public static final String API_VERSION = "2019-08-23";  // 国际站版本
    public static final String POST_REQUEST_ACTION = "SubmitTask";
    public static final String GET_REQUEST_ACTION = "GetTaskResult";
    // 请求参数
    public static final String KEY_APP_KEY = "appkey";
    public static final String KEY_FILE_LINK = "file_link";
    public static final String KEY_VERSION = "version";
    public static final String KEY_ENABLE_WORDS = "enable_words";
    // 响应参数
    public static final String KEY_TASK = "Task";
    public static final String KEY_TASK_ID = "TaskId";
    public static final String KEY_STATUS_TEXT = "StatusText";
    public static final String KEY_RESULT = "Result";
    // 状态值
    public static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_RUNNING = "RUNNING";
    private static final String STATUS_QUEUEING = "QUEUEING";


    public static boolean aiParseAudio(String fileLink, ASRResponse response) {
        boolean result = true;
        ASRConfigProperties configProperties = SpringContext.getBean("baseConfig", BaseConfig.class).getAsr();
        try {
            String taskId = submitFileTransRequest(configProperties.getAppKey(), fileLink);
            String res = getFileTransResult(taskId);
            response.setIsSuccess(IsSuccessEnum.SUCCESS);
            response.setResult(res);
        } catch (Exception e) {
            response.setIsSuccess(IsSuccessEnum.FAILURE);
            response.setError(e.getMessage());
            return false;
        }
        return result;
    }

    private static String submitFileTransRequest(String appKey, String fileLink) throws ClientException {
        /*
         * 1. 创建CommonRequest，设置请求参数。
         */
        CommonRequest postRequest = new CommonRequest();
        // 设置域名
        postRequest.setDomain(DOMAIN);
        // 设置API的版本号，格式为YYYY-MM-DD。
        postRequest.setVersion(API_VERSION);
        // 设置action
        postRequest.setAction(POST_REQUEST_ACTION);
        // 设置产品名称
        postRequest.setProduct(PRODUCT);
        /*
         * 2. 设置录音文件识别请求参数，以JSON字符串的格式设置到请求Body中。
         */
        Map<String, Object> params = new HashMap<>();
//        JSONObject taskObject = new JSONObject();
        // 设置appkey
        params.put(KEY_APP_KEY, appKey);
        // 设置音频文件访问链接
        params.put(KEY_FILE_LINK, fileLink);
        // 新接入请使用4.0版本，已接入（默认2.0）如需维持现状，请注释掉该参数设置。
        params.put(KEY_VERSION, "4.0");
        // 设置是否输出词信息，默认为false，开启时需要设置version为4.0及以上。
        params.put(KEY_ENABLE_WORDS, true);
        String task = JSONUtil.toJsonStr(params);
        log.info("提交录音识别解析任务:{}", task);
        // 设置以上JSON字符串为Body参数。
        postRequest.putBodyParameter(KEY_TASK, task);
        // 设置为POST方式的请求。
        postRequest.setMethod(MethodType.POST);
        // postRequest.setHttpContentType(FormatType.JSON);    //当aliyun-java-sdk-core 版本为4.6.0及以上时，请取消该行注释
        /*
         * 3. 提交录音文件识别请求，获取录音文件识别请求任务的ID，以供识别结果查询使用。
         */
        String taskId = null;
        IAcsClient client = SpringContext.getBean("acsClient", IAcsClient.class);
        CommonResponse postResponse = client.getCommonResponse(postRequest);
        log.info("提交录音文件识别请求的响应：{}", postResponse.getData());
        if (postResponse.getHttpStatus() == 200) {
            JSON result = JSONUtil.parse(postResponse.getData());
            String statusText = result.getByPath(KEY_STATUS_TEXT, String.class);
            if (STATUS_SUCCESS.equals(statusText)) {
                taskId = result.getByPath(KEY_TASK_ID, String.class);
                return taskId;
            }
        }
        log.info("提交录音文件识别请求失败,原因:{}",JSONUtil.toJsonStr(postResponse));
        throw new RuntimeException("提交录音文件识别请求失败");
    }

    private static String getFileTransResult(String taskId) throws ClientException, InterruptedException {
        /*
         * 1. 创建CommonRequest，设置任务ID。
         */
        CommonRequest getRequest = new CommonRequest();
        // 设置域名
        getRequest.setDomain(DOMAIN);
        // 设置API版本
        getRequest.setVersion(API_VERSION);
        // 设置action
        getRequest.setAction(GET_REQUEST_ACTION);
        // 设置产品名称
        getRequest.setProduct(PRODUCT);
        // 设置任务ID为查询参数
        getRequest.putQueryParameter(KEY_TASK_ID, taskId);
        // 设置为GET方式的请求
        getRequest.setMethod(MethodType.GET);
        /*
         * 2. 提交录音文件识别结果查询请求
         * 以轮询的方式进行识别结果的查询，直到服务端返回的状态描述为“SUCCESS”或错误描述，则结束轮询。
         */
        String result = null;
        IAcsClient client = SpringContext.getBean("acsClient", IAcsClient.class);
        while (true) {

            CommonResponse getResponse = client.getCommonResponse(getRequest);
            log.info("识别查询结果：{}", getResponse.getData());
            if (getResponse.getHttpStatus() != 200) {
                log.info("");
                throw new RuntimeException("查询录音识别结果失败");
            }
            JSONObject rootObj = JSONUtil.parseObj(getResponse.getData());
            String statusText = rootObj.getStr(KEY_STATUS_TEXT);
            if (STATUS_RUNNING.equals(statusText) || STATUS_QUEUEING.equals(statusText)) {
                // 继续轮询，注意设置轮询时间间隔。
                Thread.sleep(10000);
            } else {
                // 状态信息为成功，返回识别结果；状态信息为异常，返回空。
                if (STATUS_SUCCESS.equals(statusText)) {
                    result = rootObj.getStr(KEY_RESULT);
                    // 状态信息为成功，但没有识别结果，则可能是由于文件里全是静音、噪音等导致识别为空。
                    if (result == null) {
                        result = "";
                    }
                    log.info("查询到的录音识别结果是:{}", result);
                    break;
                }
                throw new RuntimeException("查询录音识别结果，阿里云返回" + statusText);
            }

        }
        return result;
    }
}
