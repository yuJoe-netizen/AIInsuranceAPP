package com.example.mq.consumer;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;

import java.util.ArrayList;
import java.util.List;

public class TestConsumer {

    public static void main(String[] args) {
        MQClient mqClient = new MQClient(
                // 设置HTTP协议客户端接入点，进入消息队列RocketMQ版控制台实例详情页面的接入点区域查看。
                "http://1471012666615782.mqrest.cn-shanghai.aliyuncs.com",
                // 请确保环境变量ALIBABA_CLOUD_ACCESS_KEY_ID、ALIBABA_CLOUD_ACCESS_KEY_SECRET已设置。
                // AccessKey ID，阿里云身份验证标识。
                "",
                // AccessKey Secret，阿里云身份验证密钥。
                ""
        );

        // 消息所属的Topic，在消息队列RocketMQ版控制台创建。
        //不同消息类型的Topic不能混用，例如普通消息的Topic只能用于收发普通消息，不能用于收发其他类型的消息。
        final String topic = "TIP_AIAgentDemo";
        // 您在消息队列RocketMQ版控制台创建的Group ID。
        final String groupId = "GID_AIAgentDemo";
        // Topic所属的实例ID，在消息队列RocketMQ版控制台创建。
        // 若实例有命名空间，则实例ID必须传入；若实例无命名空间，则实例ID传入null空值或字符串空值。实例的命名空间可以在消息队列RocketMQ版控制台的实例详情页面查看。
        final String instanceId = "MQ_INST_1471012666615782_BZHMiFI8";

        final MQConsumer consumer;
        if (instanceId != null && instanceId != "") {
            consumer = mqClient.getConsumer(instanceId, topic, groupId, null);
        } else {
            consumer = mqClient.getConsumer(topic, groupId);
        }

        // 在当前线程循环消费消息，建议多开个几个线程并发消费消息。
        do {
            List<Message> messages = null;

            try {
                // 长轮询消费消息。
                // 长轮询表示如果Topic没有消息,则请求会在服务端挂起3s，3s内如果有消息可以消费则立即返回客户端。
                messages = consumer.consumeMessage(
                        3,// 一次最多消费3条消息（最多可设置为16条）。
                        3// 长轮询时间3秒（最多可设置为30秒）。
                );
            } catch (Throwable e) {
                e.printStackTrace();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // Topic中没有消息可消费。
            if (messages == null || messages.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + ": no new message, continue!");
                continue;
            }

            // 处理业务逻辑。
            for (Message message : messages) {
                System.out.println("Receive message: " + message);
            }

            // 消息重试时间到达前若不确认消息消费成功，则消息会被重复消费。
            // 消息句柄有时间戳，同一条消息每次消费的时间戳都不一样。
            {
                List<String> handles = new ArrayList<String>();
                for (Message message : messages) {
                    handles.add(message.getReceiptHandle());
                }

                try {
                    consumer.ackMessage(handles);
                } catch (Throwable e) {
                    // 某些消息的句柄可能超时，会导致消息消费状态确认不成功。
                    if (e instanceof AckMessageException) {
                        AckMessageException errors = (AckMessageException) e;
                        System.out.println("Ack message fail, requestId is:" + errors.getRequestId() + ", fail handles:");
                        if (errors.getErrorMessages() != null) {
                            for (String errorHandle :errors.getErrorMessages().keySet()) {
                                System.out.println("Handle:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
                                        + ", ErrorMsg:" + errors.getErrorMessages().get(errorHandle).getErrorMessage());
                            }
                        }
                        continue;
                    }
                    e.printStackTrace();
                }
            }
        } while (true);
    }
}
