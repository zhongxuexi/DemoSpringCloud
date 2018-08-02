package com.jess.controller;

import com.jess.service.MemberFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@RestController
public class MemberFeginController {
    @Autowired
    private MemberFegin memberFegin;

    @RequestMapping(value = "/members")
    public Map<String,Object> getMembers() throws Exception{
        System.out.println("order fegin工程调用member工程");
        Map<String,Object> map = new HashMap<>();
        map.put("show",memberFegin.getAll());
        return map;
    }
    @RequestMapping(value = "/getInfo")
    public String getInfo(){
        return "getInfo";
    }
}
