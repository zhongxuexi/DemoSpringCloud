package com.jess.service;

import com.jess.fallback.MemberFallBack;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@FeignClient(value = "server-member",fallback = MemberFallBack.class)
public interface MemberFegin {

    @RequestMapping(value = "/getAll")
    List<Object> getAll() throws Exception;
}
