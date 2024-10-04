package com.example.api.aliyun.init;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.example.api.config.WebSocketUtil;
import com.example.api.model.CallMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * 按照预设文件输出通话文本
 */
@Slf4j
@Component
public class FixCallMessagePusher {


    @Getter
    private boolean isStart=false;

    private final WebSocketUtil webSocketUtil;

    private final List<Timer> timers = new ArrayList<>();

    public FixCallMessagePusher(WebSocketUtil webSocketUtil) {
        this.webSocketUtil = webSocketUtil;
    }


    public void pushMsg(){
        log.info("开始推送消息");
        if (isStart){
            log.info("已经开始推送,无需重新递交推送任务");
            return;
        }
        List<CallMessage> callMessages = getMessageFromFile();

        for (CallMessage message : callMessages) {
            long needTime=message.getDelay();
            try {
                Thread.sleep(needTime*1000);
                log.info("发送消息");
                webSocketUtil.sendMessageTo(JSONUtil.toJsonStr(message), "yujiangjun");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }


    private List<CallMessage> getMessageFromFile(){
        List<CallMessage> messages = new ArrayList<>();
        try (InputStream ins = this.getClass().getClassLoader().getResourceAsStream("fixMessage.json")) {
            byte[] bytes = IoUtil.readBytes(ins);
            String json = new String(bytes, StandardCharsets.UTF_8);
            messages = JSONUtil.toList(json, CallMessage.class);
        } catch (Exception e) {
            log.info("读取预设文件发生了异常:",e);
        }
        return messages;
    }


    public void init(){
        isStart=false;
        for (Timer timer : timers) {
            timer.cancel();
        }
    }
}
