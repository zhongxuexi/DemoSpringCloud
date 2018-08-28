package com.jess.config.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by zhongxuexi on 2018/8/28.
 */
@Component
@RabbitListener(queues = "okong")   //监听okong队列
public class Consumer {
    /**
     * 消息的消费者
     * @param message
     */
    @RabbitHandler
    public void process(String message){
        System.out.println("接收到的消息为:{"+message+"}");
    }
}
