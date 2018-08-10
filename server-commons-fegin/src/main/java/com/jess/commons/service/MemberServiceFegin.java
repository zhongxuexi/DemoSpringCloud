package com.jess.commons.service;

import com.jess.commons.fallback.FallBackMemberServiceFegin;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@Service
@FeignClient(value = "server-member",fallback = FallBackMemberServiceFegin.class)
public interface MemberServiceFegin {

    @ApiOperation(value="测试接口")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    String test(@RequestParam("desc") String desc) throws Exception;
}
