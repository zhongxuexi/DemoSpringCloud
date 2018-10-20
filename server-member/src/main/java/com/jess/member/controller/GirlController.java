package com.jess.member.controller;

import com.jess.common.util.Result;
import com.jess.member.service.GirlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhongxuexi on 2018/8/28.
 */
@RestController
@CrossOrigin
@Api(value="女孩管理",tags = {"女孩管理接口"})
@RefreshScope//此注解，是在访问/refresh后服务端加载新配置，自动把新配置注入
@RequestMapping("/girl")
public class GirlController {
    @Autowired
    private GirlService girlService;

    @ApiOperation(value="根据用户名字模糊查询女孩信息")
    @GetMapping(value = "/findByName")
    public Result findByName(@RequestParam(required = false,value = "name",defaultValue = "")String name){
        return girlService.findByName(name);
    }
}
