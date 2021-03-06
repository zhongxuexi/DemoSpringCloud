//package com.jess.member.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by zhongxuexi on 2018/8/28.
// */
//@RestController
//@CrossOrigin
//@Api(value="发送消息",tags={"发送消息"})
//@RequestMapping("/mqsender")
//public class MqSenderController {
//    @Autowired
//    AmqpTemplate rabbitmqTemplate;
//
//    @ApiOperation(value="okong发送消息")
//    @GetMapping("/okong")
//    public String okongSend(String msg){
//        //发送消息
//        rabbitmqTemplate.convertAndSend("okong",msg);
//        return "消息："+msg+"，已发送";
//    }
//
//    @ApiOperation(value="jess发送消息")
//    @GetMapping("/jess")
//    public String jessSend(String msg){
//        //发送消息
//        rabbitmqTemplate.convertAndSend("jess",msg);
//        return "消息："+msg+"，已发送";
//    }
//}
