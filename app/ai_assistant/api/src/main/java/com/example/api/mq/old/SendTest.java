//package com.example.api.mq.old;
//
//import com.aliyun.openservices.ons.api.*;
//
//import java.util.Date;
//import java.util.Properties;
//
//public class SendTest {
//
//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        // 请确保环境变量ALIBABA_CLOUD_ACCESS_KEY_ID、ALIBABA_CLOUD_ACCESS_KEY_SECRET已设置。
//        // AccessKey ID，阿里云身份验证标识。
//        properties.put(PropertyKeyConst.AccessKey, "fl1hP5e5wV0hmA1s");
//        // AccessKey Secret，阿里云身份验证密钥。
//        properties.put(PropertyKeyConst.SecretKey, "6cOhhr3FA1g9G6V6");
//        //设置发送超时时间，单位：毫秒。
//        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
//        // 设置TCP接入域名，进入云消息队列 RocketMQ 版控制台实例详情页面的接入点区域查看。
//        properties.put(PropertyKeyConst.NAMESRV_ADDR, "rmq-cn-g6z3wu29j0a.cn-shanghai.rmq.aliyuncs.com:8080");
//        Producer producer = ONSFactory.createProducer(properties);
//        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
//
//        producer.start();
//
//        //循环发送消息。
//        for (int i = 0; i < 5; i++){
//            Message msg = new Message(
//                    // 普通消息所属的Topic，切勿使用普通消息的Topic来收发其他类型的消息。
//                    "AIAgent_demo",
//                    // Message Tag可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在消息队列RocketMQ版的服务器过滤。
//                    // Tag的具体格式和设置方法，请参见Topic与Tag最佳实践。
//                    "TagA",
//                    // Message Body可以是任何二进制形式的数据，消息队列RocketMQ版不做任何干预。
//                    // 需要Producer与Consumer协商好一致的序列化和反序列化方式。
//                    "Hello MQ".getBytes());
//            // 设置代表消息的业务关键属性，请尽可能全局唯一。
//            // 以方便您在无法正常收到消息情况下，可通过消息队列RocketMQ版控制台查询消息并补发。
//            // 注意：不设置也不会影响消息正常收发。
//            msg.setKey("ORDERID_" + i);
//            try {
//                SendResult sendResult = producer.send(msg);
//                // 同步发送消息，只要不抛异常就是成功。
//                if (sendResult != null) {
//                    System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
//                }
//            }
//            catch (Exception e) {
//                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
//                System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
//                e.printStackTrace();
//            }
//        }
//
//        // 在应用退出前，销毁Producer对象。
//        // 注意：销毁Producer对象可以节约系统内存，若您需要频繁发送消息，则无需销毁Producer对象。
//        producer.shutdown();
//    }
//}