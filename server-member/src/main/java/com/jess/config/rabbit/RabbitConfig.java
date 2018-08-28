package com.jess.config.rabbit;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhongxuexi on 2018/8/28.
 */
@Configuration
public class RabbitConfig {
    /**
     * 定义一个名为:okong的队列
     * @return
     */
    @Bean
    public Queue okongQueue(){
        return new Queue("okong");
    }
}
