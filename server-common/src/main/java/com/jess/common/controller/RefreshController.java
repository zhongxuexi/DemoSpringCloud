package com.jess.common.controller;

import com.google.common.collect.Lists;
import com.jess.common.util.*;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/10/20 15:05
 * @Description:
 */
@RestController
@Api(value = "手动更新配置", tags = {"手动更新配置"})
@RequestMapping(value = "/common")
public class RefreshController {
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "更新配置接口", notes = "请求POST /refresh")
    @GetMapping(value = "/configRefresh")
    public Result configRefresh(){
        StringBuffer resultSb=new StringBuffer();
        StringBuffer errorSb=new StringBuffer();

        List<String> list= Lists.newArrayList();
        list.add("server-common");
        list.add("server-member");
        list.add("server-order");
        list.add("server-zuul");
        Date startDate= new Date();
        list.forEach(item->{
            try {
                String result = restTemplate.postForObject("http://"+item+"/refresh", null, String.class);
                LogUtil.logger.info(item+"配置已刷新-->"+result);
                resultSb.append(item+"配置已刷新-->"+result+",");
            }catch (Exception e){
                LogUtil.logger.error(e.getMessage());
                errorSb.append(item+"配置刷新失败，报错信息："+e.getMessage());
            }
        });
        Date endDate= new Date();
        resultSb.append("刷新耗时："+DateUtil.getSecondSub(startDate,endDate)+"秒。");
        if(StringUtils.isNoneBlank(errorSb)){
            return Result.error(CodeMsg.SERVER_EXCEPTION,errorSb.toString());
        }
        return Result.success(resultSb.toString(),1);
    }
}
