package com.jess.member;

import com.google.common.collect.Maps;
import com.jess.common.util.ObjectToMapUtil;
import com.jess.member.entity.Girl;
import com.jess.member.entity.User;
import com.jess.member.service.GirlService;
import com.jess.member.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Auther: zhongxuexi
 * @Date: 2018/11/3 11:17
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test2 {
    @Autowired
    private UserService userService;
    @Autowired
    private GirlService girlService;
    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void test21() throws Exception {
        User user = userService.findUserById(18L);
        Girl girl = girlService.findById(3);
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id,realname,sex,age", user);
        map.put("id,age,cupSize,name", girl==null ? new Girl():girl);
        Map<String, Object> combinationMap = ObjectToMapUtil.getCombinationMap(map);
        System.out.println("============");
        System.out.println(combinationMap.toString());
    }

    @Test
    public void test22(){
        System.out.println(jedisCluster.get("zhong"));
    }
}
