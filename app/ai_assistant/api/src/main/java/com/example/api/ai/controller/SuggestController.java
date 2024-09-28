package com.example.api.ai.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.api.ai.dto.PressurePointInfoDTO;
import com.example.api.ai.dto.PromptListDTO;
import com.example.api.ai.dto.PromptListReqDTO;
import com.example.common.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suggest")
@Slf4j
public class SuggestController {


    @GetMapping("/getSuggest")
    public RespVO<List<PromptListDTO>> getAiSuggestion(PromptListReqDTO request) {
        PressurePointInfoDTO pointInfoDTO = parseLocalJson();
        List<PromptListDTO> res = new ArrayList<>();
        for (PromptListDTO promptListDTO : pointInfoDTO.getPromptList()) {
            if (StrUtil.contains(request.getQuestionPointKeyWords(),promptListDTO.getQuestionPoint())){
                res.add(promptListDTO);
            }
        }
        return RespVO.success(res);
    }

    @GetMapping("/getCuiShouInfo")
    public RespVO<Map<String, Object>> getCuiShouInfo(String context) {
        try (InputStream ins = this.getClass().getClassLoader().getResourceAsStream("CCPlan.json")) {
            byte[] bytes = IoUtil.readBytes(ins, ins.available());
            String json = new String(bytes, StandardCharsets.UTF_8);
            return RespVO.success(JSONUtil.toBean(json, new TypeReference<Map<String, Object>>() {
            }, true));
        } catch (Exception e) {
            log.info("e:", e);
        }
        throw new RuntimeException("发送了错误");
    }

    @GetMapping("/getPressurePoint")
    public RespVO<String> getPressurePoint(String context) {
        PressurePointInfoDTO pointInfoDTO = parseLocalJson();
        return RespVO.success(pointInfoDTO.getPressurePoint());
    }


    private PressurePointInfoDTO parseLocalJson(){
        PressurePointInfoDTO bean = new PressurePointInfoDTO();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("AIAgent.json")) {
            Thread.sleep(2000);
            byte[] bytes = IoUtil.readBytes(in);
            String json = StrUtil.str(bytes, StandardCharsets.UTF_8);
            bean = JSONUtil.toBean(json, new TypeReference<PressurePointInfoDTO>() {
            }, true);
        } catch (Exception e) {
            log.error("获取施压点发生了异常:", e);
        }
        return bean;
    }
}
