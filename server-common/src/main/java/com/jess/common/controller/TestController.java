package com.jess.common.controller;

import com.jess.common.component.redis.RedisClient;
import com.jess.common.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/11 22:11
 * @Description: 微服务配置自动刷新
 */
@RestController
@CrossOrigin
@Api(value = "测试接口",tags = {"测试接口"})
public class TestController {
    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "测试redis客户端", notes = "test redis")
    @GetMapping(value = "/testRedis")
    public String testRedis(@RequestParam("key") String key,@RequestParam("value") String value){
        redisClient.set(key,value);
        return redisClient.get(key);
    }
}
