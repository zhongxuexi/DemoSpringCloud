package com.jess.common.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jess.common.component.redis.RedisClient;
import com.jess.common.util.HttpUtils;
import com.jess.common.util.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/11 22:11
 * @Description: 测试接口
 */
@RestController
@CrossOrigin
@Api(value = "测试接口", tags = {"测试接口"})
public class TestController {
    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "测试redis客户端", notes = "test redis")
    @GetMapping(value = "/testRedis")
    public String testRedis(@RequestParam("key") String key, @RequestParam("value") String value) {
        redisClient.set(key, value);
        return redisClient.get(key);
    }

    @ApiOperation(value = "测试get获取request参数", notes = "测试get获取request参数")
    @RequestMapping(value = "/testGet", method = RequestMethod.GET)
    public Map<String, Object> getProcess(HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = HttpUtils.getRequestMap(request);
        return resultMap;
    }

    @ApiOperation(value = "测试post获取request参数", notes = "测试post获取request参数")
    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", dataType = "string", example = "xingguo"),
//            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", dataType = "int")
//    })
    public User postProcess(HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = HttpUtils.getRequestMap(request);
        //System.out.println(((String[])resultMap.get("name"))[0]);
        //System.out.println(((String[])resultMap.get("age"))[0]);
        User newUser = HttpUtils.mapToBean(resultMap, new User());
        return newUser;
    }
}
