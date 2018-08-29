package com.jess.common.component.rabbitMq;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/8/29 21:31
 * @Description:定义队列
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
