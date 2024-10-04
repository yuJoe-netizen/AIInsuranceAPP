package com.example.api.controller;

import com.example.api.aliyun.init.FixCallMessagePusher;
import com.example.common.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/callText")
@Slf4j
public class CallTextPusherController {


    private final FixCallMessagePusher fixCallMessagePusher;

    public CallTextPusherController(FixCallMessagePusher fixCallMessagePusher) {
        this.fixCallMessagePusher = fixCallMessagePusher;
    }

    @PostMapping("/push")
    public RespVO<Void> pushWs(){
        log.info("接收到前端的请求，开始进行ws推送");
        ExecutorService poll = Executors.newFixedThreadPool(5);
        poll.submit(fixCallMessagePusher::pushMsg);
        log.info("推送完毕。。。。。。。。。");
        return RespVO.success(null);
    }

}
