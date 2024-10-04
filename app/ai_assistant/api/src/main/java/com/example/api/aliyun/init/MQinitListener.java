package com.example.api.aliyun.init;

import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;
import com.example.api.ai.config.GetCallTextSetting;
import com.example.api.config.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class MQinitListener implements CommandLineRunner {

    private final MQConsumer mqConsumer;
    private final WebSocketUtil webSocketUtil;
    private final FixCallMessagePusher fixCallMessagePusher;

    private final GetCallTextSetting getCallTextSetting;

    public MQinitListener(MQConsumer mqConsumer, WebSocketUtil webSocketUtil, FixCallMessagePusher fixCallMessagePusher, GetCallTextSetting getCallTextSetting) {
        this.mqConsumer = mqConsumer;
        this.webSocketUtil = webSocketUtil;
        this.fixCallMessagePusher = fixCallMessagePusher;
        this.getCallTextSetting = getCallTextSetting;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("项目初始化任务执行。。。。。。");
        do {
            List<Message> messages = null;

            try {
                // 长轮询消费消息。
                // 长轮询表示如果Topic没有消息,则请求会在服务端挂起3s，3s内如果有消息可以消费则立即返回客户端。
                messages = mqConsumer.consumeMessage(
                        3,// 一次最多消费3条消息（最多可设置为16条）。
                        3// 长轮询时间3秒（最多可设置为30秒）。
                );
            } catch (Throwable e) {
                log.error("",e);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    log.error("",e1);
                }
            }
            // Topic中没有消息可消费。
            if (messages == null || messages.isEmpty()) {
//                log.info("{}: no new message, continue!",Thread.currentThread().getName());
                continue;
            }

            // 处理业务逻辑。
            Integer sendMessageSwitch = getCallTextSetting.getSendMessageSwitch();
            for (Message message : messages) {
                log.info("body:{}",message.getMessageBodyString());
                log.info("开关:{}",sendMessageSwitch);
                if (Objects.equals(sendMessageSwitch,1)){
                    log.info("推送实时语音文本");
                    webSocketUtil.sendMessageTo(message.getMessageBodyString(),"yujiangjun");
                }else {
                    log.info("推送预设文本");
                    fixCallMessagePusher.pushMsg();
                }
            }

            // 消息重试时间到达前若不确认消息消费成功，则消息会被重复消费。
            // 消息句柄有时间戳，同一条消息每次消费的时间戳都不一样。
            {
                List<String> handles = new ArrayList<String>();
                for (Message message : messages) {
                    handles.add(message.getReceiptHandle());
                }

                try {
                    mqConsumer.ackMessage(handles);
                } catch (Throwable e) {
                    // 某些消息的句柄可能超时，会导致消息消费状态确认不成功。
                    if (e instanceof AckMessageException) {
                        AckMessageException errors = (AckMessageException) e;
                        log.info("Ack message fail, requestId is:{}, fail handles:",errors.getRequestId());
                        if (errors.getErrorMessages() != null) {
                            for (String errorHandle :errors.getErrorMessages().keySet()) {
                                log.info("Handle:{}, ErrorCode:{}, ErrorMsg:{}",errorHandle ,errors.getErrorMessages().get(errorHandle).getErrorCode()
                                        ,errors.getErrorMessages().get(errorHandle).getErrorMessage());
                            }
                        }
                        continue;
                    }
                    log.error("",e);
                }
            }
        } while (true);
    }
}
