package com.jess.member.controller;

import com.jess.common.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/10/20 15:05
 * @Description:
 */
@RestController
@Api(value = "server-member手动更新配置", tags = {"server-member微服务"})
public class RefreshController {

    @ApiOperation(value = "server-member更新配置接口", notes = "/refresh")
    @PostMapping(value = "/refresh")
    public void refreshConfig(){
       // String url="http://localhost:8020/jess/member/refresh";
       // HttpUtils.refresh(url);
    }
}
