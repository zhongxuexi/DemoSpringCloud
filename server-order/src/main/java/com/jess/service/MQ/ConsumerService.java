package com.jess.service.MQ;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.PostLoad;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/5 22:22
 * @Description:
 */
@Component
public class ConsumerService {
    /**
     * 消息的消费者1
     * @param message
     */
    @RabbitListener(queues = "okong")   //监听okong队列
    @RabbitHandler
    public void process1(String message){
        System.out.println("接收到okong消息为'" + message + "'");
    }
    /**
     * 消息的消费者2
     * @param message
     */
    @RabbitListener(queues = "jess")   //监听jess队列
    @RabbitHandler
    public void process2(String message){
        System.out.println("接收到jess消息为'" + message + "'");
    }
}
