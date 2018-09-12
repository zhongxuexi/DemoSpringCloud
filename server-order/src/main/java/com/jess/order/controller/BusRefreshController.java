package com.jess.order.controller;

import com.jess.common.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/11 22:11
 * @Description: 微服务配置手动刷新
 */
@RestController
@CrossOrigin
@Api(value = "微服务配置刷新",tags = {"微服务配置刷新接口"})
public class BusRefreshController {

    @ApiOperation(value = "手动刷新配置", notes = "手动刷新配置")
    @RequestMapping(value = "/refresh",method = RequestMethod.POST)
    public String refresh(){
        HttpUtils.refresh();
        return "success refresh config";
    }
//    @ApiOperation(value = "bus自动刷新配置", notes = "bus自动刷新配置")
//    @RequestMapping(value = "/bus/refresh",method = RequestMethod.POST)
//    public String busRefresh(){
//        HttpUtils.refresh();
//        return "bus success refresh config";
//    }
}
