package com.jess;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jess.jsonBean.TeamListJson;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

/**
 * 解析外部 json 文件的类，解析前先在网上在线生成对应的java类
 * Created by zhongxuexi on 2018/7/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    @Test
    public void test1(){
        try {
            String jsonData = FileUtils.readFileToString(ResourceUtils.
                    getFile("classpath:static/data/teamlist.json"), Charset.forName("UTF-8"));
            //使用JSONArray转换
            JSONArray jsonArray = JSONArray.parseArray(jsonData);
            //获得jsonArray的所有对象
            //定义接受的集合
            List<TeamListJson> teamList = Lists.newArrayList();
            for(int i=0;i<jsonArray.size();i++){
                Object o=jsonArray.get(i);
                JSONObject jsonObject=JSONObject.parseObject(o.toString());
                //转为java实体对象
                TeamListJson team=JSONObject.toJavaObject(jsonObject, TeamListJson.class);
                teamList.add(team);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
