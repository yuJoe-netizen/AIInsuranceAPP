package com.example.api.aliyun.init;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.example.api.config.WebSocketUtil;
import com.example.api.model.CallMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 按照预设文件输出通话文本
 */
@Slf4j
@Component
public class FixCallMessagePusher {


    @Getter
    @Setter
    private boolean isStart=false;
    Future<Boolean> task = null;
    private final WebSocketUtil webSocketUtil;


    public FixCallMessagePusher(WebSocketUtil webSocketUtil) {
        this.webSocketUtil = webSocketUtil;
    }


    public void pushMsg(){
        log.info("开始推送消息");
        if (isStart){
            log.info("已经开始推送,无需重新递交推送任务");
            return;
        }
        isStart=true;
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        task = executorService.submit(this::addPushTask);
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

    private boolean addPushTask(){
        List<CallMessage> callMessages = getMessageFromFile();

        for (CallMessage message : callMessages) {
            long needTime=message.getDelay();
            try {
                Thread.sleep(needTime*1000);
                log.info("发送消息");
                webSocketUtil.sendMessageTo(JSONUtil.toJsonStr(message), "yujiangjun");
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
        }
        return true;
    }


    public void init(){
        log.info("推送进行初始化。。。");
        isStart=false;

        if (task!=null){
            task.cancel(true);
        }
    }
}
