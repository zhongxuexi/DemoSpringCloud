package com.jess.controller;

import com.jess.common.component.redis.RedisClient;
import com.jess.common.service.MemberServiceFegin;
import com.jess.common.util.Result;
import com.jess.common.util.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhongxuexi on 2018/6/5.
 */
@RestController
@CrossOrigin
@Api(value="订单管理")
public class OrderController {
    @Autowired
    private MemberServiceFegin memberServiceFegin;
    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value="测试发送邮件", notes="mailTest")
    @GetMapping(value = "/testEmail")
    public Result testEmail(@RequestParam("title") String title,
                            @RequestParam("body") String body) throws Exception {
        EmailUtil.sendHtmlMail("jess.zhong@aliyun.com",title,body);
        return Result.success();
    }

    @ApiOperation(value="测试Feign远程调用", notes="testFeign")
    @GetMapping(value = "/testFeign")
    public String test(@RequestParam("desc") String desc) throws Exception {
        System.out.println("测试feign远程调用");
        return  memberServiceFegin.test(desc);
    }

}
