package com.jess.controller;

import com.jess.service.OrderMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/5.
 */
@RestController
public class OrderController {
    @Autowired
    private OrderMemberService orderMemberService;

    @RequestMapping(value = "/getAllMembers")
    public List<Object> getAllMembers(){
        System.out.println("订单服务开始调用会员服务");
        return orderMemberService.getOrderMembers();
    }
    @RequestMapping(value = "/getOrderServerApi")
    public String getOrderServerApi(){
        return "这是 order 服务工程";
    }
}
