package com.example.api.controller;

import com.example.api.ai.config.GetCallTextSetting;
import com.example.api.aliyun.init.FixCallMessagePusher;
import com.example.api.aliyun.utils.ASRUtil;
import com.example.api.aliyun.vo.ASRResponse;
import com.example.common.RespVO;
import com.example.db.entity.CallAudio;
import com.example.db.mapper.CallAudioMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/callText")
@Slf4j
public class CallTextPusherController {


    private final FixCallMessagePusher fixCallMessagePusher;

    private final GetCallTextSetting getCallTextSetting;

    private final CallAudioMapper callAudioMapper;

    public CallTextPusherController(FixCallMessagePusher fixCallMessagePusher, GetCallTextSetting getCallTextSetting, CallAudioMapper callAudioMapper) {
        this.fixCallMessagePusher = fixCallMessagePusher;
        this.getCallTextSetting = getCallTextSetting;
        this.callAudioMapper = callAudioMapper;
    }

    @PostMapping("/push")
    public RespVO<Void> pushWs(){
        log.info("接收到前端的请求，开始进行ws推送");
        fixCallMessagePusher.pushMsg();
        log.info("推送完毕。。。。。。。。。");
        return RespVO.success(null);
    }

    @GetMapping("/changeGetTextType")
    public RespVO<Void> changeGetTextType(Integer type){
        if (type != 1 && type != 2) {
            throw new RuntimeException("type参数错误,type=1 读取通话语音 type=2 通过阿里云实时语音文本");
        }
        getCallTextSetting.setSendMessageSwitch(type);
        return RespVO.success(null);
    }

    @GetMapping("/getTextType")
    public RespVO<Integer> changeGetTextType(){
        return RespVO.success(getCallTextSetting.getSendMessageSwitch());
    }

    @GetMapping("/getAudioTxt")
    public RespVO<String> getAudioTxt(){
        ASRResponse response = new ASRResponse();
        ASRUtil.aiParseAudio("https://fgrecording.oss-cn-shanghai.aliyuncs.com/1/20241008/a94e2c0a-352b-4142-bb68-14d8f4f849dd.mp3",response);
        return RespVO.success(response.getResult());
    }


    @GetMapping("/getAllAudio")
    public RespVO<List<CallAudio>> getAllAudio(){
        return RespVO.success(callAudioMapper.selectList(null));
    }
}
