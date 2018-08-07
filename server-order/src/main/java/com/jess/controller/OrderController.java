package com.jess.controller;

import com.jess.commons.service.MemberServiceFegin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhongxuexi on 2018/6/5.
 */
@Controller
@RequestMapping(value = "/order")
@Api(value="订单管理")
public class OrderController {
    @Autowired
    private MemberServiceFegin memberServiceFegin;

    @ApiOperation(value="测试Feign远程调用", notes="feignTest")
    @GetMapping(value = "/feignTest")
    public String feignTest() throws Exception {
        System.out.println("测试feign远程调用");
        return  memberServiceFegin.test();
    }

}
