package com.jess.controller;

import com.github.pagehelper.PageHelper;
import com.jess.entity.Member;
import com.jess.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by zhongxuexi on 2018/6/5.
 */
@RestController
public class MemberController {
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private IMemberService iMemberService;
    private int count=0;
    @RequestMapping(value = "/listAll")
    public Map<String,Object> getAll(ModelAndView m) throws Exception {
        /*try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }*/
       count++;
        System.out.println("被调用了："+count+"次");
        Map<String,Object> map = new TreeMap<>();
        map.put("code","200");
        map.put("message","成功");
        map.put("data",iMemberService.getAll());
        //m.addObject("list",map);
        return map;

    }

    @RequestMapping(value = "/addMember")
    public String getMemberServerApi(Member member) throws Exception {
        Member member1=null;
        iMemberService.insertMember(member1);
        return "添加成功!";
    }
}
