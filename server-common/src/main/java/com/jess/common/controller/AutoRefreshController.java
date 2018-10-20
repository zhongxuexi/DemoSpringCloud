package com.jess.common.controller;

import com.jess.common.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/11 22:11
 * @Description: 微服务配置自动刷新
 */
@RestController
@CrossOrigin
@Api(value = "微服务配置刷新",tags = {"微服务配置刷新接口"})
public class AutoRefreshController {

    @ApiOperation(value = "bus自动刷新配置", notes = "bus自动刷新配置")
    @RequestMapping(value = "/autoRefresh",method = RequestMethod.GET)
    public void autoRefresh(){
        String url="http://localhost:8020/refresh";
        //HttpUtils.refresh(url);
    }
}
