package com.jess.controller;

import com.github.pagehelper.PageHelper;
import com.jess.entity.Member;
import com.jess.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    @RequestMapping(value = "/getAll")
    public List<Member> getAll() throws Exception {
        /*try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }*/
       count++;
        System.out.println("被调用了："+count+"次");

        return iMemberService.getAll();
        //return "富商大贾打个电话广东省";
    }

    @RequestMapping(value = "/getMemberServerApi")
    public String getMemberServerApi() {
        return "这是 member 服务工程";
    }
}
