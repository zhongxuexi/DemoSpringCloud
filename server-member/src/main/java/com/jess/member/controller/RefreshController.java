package com.jess.member.controller;

import com.jess.common.util.CodeMsg;
import com.jess.common.util.HttpUtils;
import com.jess.common.util.LogUtil;
import com.jess.common.util.Result;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/10/20 15:05
 * @Description:
 */
@RestController
@Api(value = "server-member手动更新配置", tags = {"server-member微服务"})
@RequestMapping(value = "/config")
public class RefreshController {

    @ApiOperation(value = "server-member更新配置接口", notes = "请求POST /refresh")
    @GetMapping(value = "/update")
    public Result refreshConfig(){
        String url="http://localhost:8020/jess/member/refresh";
        try {
            HttpUtils.refresh(url);
        }catch (Exception e){
            LogUtil.logger.error(e.getMessage());
            return Result.error(CodeMsg.SERVER_EXCEPTION,"server-member更新配置失败，请重试！");
        }
        return Result.success();
    }
}
