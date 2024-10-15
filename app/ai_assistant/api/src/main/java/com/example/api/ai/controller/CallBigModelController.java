//package com.example.api.ai.controller;
//
//import com.alibaba.dashscope.aigc.generation.Generation;
//import com.alibaba.dashscope.aigc.generation.GenerationParam;
//import com.alibaba.dashscope.aigc.generation.GenerationResult;
//import com.alibaba.dashscope.common.Message;
//import com.alibaba.dashscope.common.Role;
//import com.alibaba.dashscope.exception.ApiException;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import com.example.common.RespVO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//
///**
// * 大模型调用
// */
//@RestController
//@RequestMapping("/bigModel")
//@Slf4j
//public class CallBigModelController {
//
//    private static final String MODEL = "qwen-plus";
//
//
//    /**
//     * 调用阿里云通义千问-Plus
//     * @return
//     */
//    @RequestMapping("/getBigModel")
//    public RespVO<String> getBigModel(){
//        // 建议dashscope SDK的版本 >= 2.12.0
//        log.info("开始调用阿里云大模型");
//        try {
//            GenerationResult result = callWithMessage();
//            log.info("调用大模型结果:{}",result);
//        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
//            // 使用日志框架记录异常信息
//            log.error("An error occurred while calling the generation service: " + e.getMessage());
//        }
//        return RespVO.success("ok");
//    }
//
//    public GenerationResult callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {
//        Generation gen = new Generation();
//        Message systemMsg = Message.builder()
//                .role(Role.SYSTEM.getValue())
//                .content("You are a helpful assistant.")
//                .build();
//        Message userMsg = Message.builder()
//                .role(Role.USER.getValue())
//                .content("你是谁？")
//                .build();
//        GenerationParam param = GenerationParam.builder()
//                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
//                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
//                .model(MODEL)
//                .messages(Arrays.asList(systemMsg, userMsg))
//                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
//                .build();
//        return gen.call(param);
//    }
//}
