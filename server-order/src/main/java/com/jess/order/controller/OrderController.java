package com.jess.order.controller;

import com.google.common.collect.Maps;
//import com.jess.common.component.redis.RedisClient;
import com.jess.common.component.redis.RedisClient;
import com.jess.common.service.MemberServiceFegin;
import com.jess.common.util.LogUtil;
import com.jess.common.util.Result;
import com.jess.common.util.EmailUtil;
import com.jess.common.util.excelUtil.ExportExcel;
import com.jess.order.entity.Version;
import com.jess.order.service.VersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/6/5.
 */
@RestController
@CrossOrigin
@Api(value = "订单管理",tags = {"订单管理接口"})
@RefreshScope//此注解，是在访问/refresh后服务端加载新配置，自动把新配置注入
public class OrderController {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private MemberServiceFegin memberServiceFegin;
    @Autowired
    private VersionService versionService;

    @ApiOperation(value = "查找版本", notes = "查找版本")
    @GetMapping(value = "/findVersion")
    public Result findVersion() throws Exception {
        List<Map<String, Object>> list = versionService.find();
        return Result.success(list, list.size());
    }

    @ApiOperation(value = "导出版本信息到Excel表格", notes = "导出Excel")
    @GetMapping(value = "/exportExcel")
    public Result exportExcel() throws Exception {
        List<Map<String, Object>> list = versionService.find();
        Map<String, Object> map = list.size()>0?list.get(0): Maps.newLinkedHashMap();
        String[] headers=new String[map.keySet().size()];
        //遍历map中的键
        int i=0;
        for (String key : map.keySet()) {
            headers[i]=key;
            i++;
        }
        OutputStream out=new FileOutputStream("E:\\workSpaces\\work_space_4\\version.xls");
        ExportExcel exportExcel = new ExportExcel();
        exportExcel.exportExcel(headers,list,out);
        LogUtil.logger.info("excel导出成功");
        out.close();
        return Result.success("excel导出成功",1);
    }

    @ApiOperation(value = "测试发送邮件", notes = "testEmail")
    @GetMapping(value = "/testEmail")
    public Result testEmail(@RequestParam("title") String title,
                            @RequestParam("body") String body) throws Exception {
        EmailUtil.sendHtmlMail("jess.zhong@aliyun.com", title, body);
        return Result.success();
    }

    @ApiOperation(value = "测试Feign远程调用", notes = "testFeign")
    @GetMapping(value = "/testFeign")
    public String testFeign(@RequestParam("desc") String desc) throws Exception {
        System.out.println("测试feign远程调用");
        return memberServiceFegin.test(desc);
    }

    @ApiOperation(value = "测试redis客户端", notes = "test redis")
    @GetMapping(value = "/testRedis")
    public String testRedis(@RequestParam("key") String key){
        Version version = versionService.findById(5L);
        redisClient.set(key,version);
        return redisClient.get(key);
    }
}
