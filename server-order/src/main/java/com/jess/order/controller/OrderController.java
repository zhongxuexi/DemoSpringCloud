package com.jess.order.controller;

import com.jess.common.component.redis.RedisClient;
import com.jess.common.service.MemberServiceFegin;
import com.jess.common.util.Result;
import com.jess.common.util.EmailUtil;
import com.jess.order.service.VersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private VersionService versionService;

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

    @ApiOperation(value="查找版本", notes="查找版本")
    @GetMapping(value = "/findVersion")
    public Result findVersion() throws Exception {
        List<Map<String, Object>> list = versionService.find();
        return Result.success(list,list.size());
    }

}
