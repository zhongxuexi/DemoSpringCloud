package com.jess;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@RestController
public class ConfigController {
    @Value("${mingzi}")
    private String name;
    @Value("${age}")
    private String age;
    @Value("${wight}")
    private String wight;

    @RequestMapping(value = "/getUser")
    public String getUser()throws Exception{
        return "名字："+name+"，年龄："+age+"，体重"+wight;
    }
}
