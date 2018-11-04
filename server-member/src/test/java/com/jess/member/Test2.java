package com.jess.member;

import com.google.common.collect.Maps;
import com.jess.common.component.redis.RedisClient;
//import com.jess.common.component.redis.JedisClusterConfig;
import com.jess.common.util.ObjectToMapUtil;
import com.jess.member.entity.Girl;
import com.jess.member.entity.User;
import com.jess.member.service.GirlService;
import com.jess.member.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
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
    //@Autowired
    //private JedisCluster jedisCluster;
    @Autowired
    private RedisClient redisClient;
    //@Autowired
    //private JedisClusterConfig jedisClusterConfig;
    @Autowired
    private RedisTemplate redisTemplate;

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
        //redisTemplate.boundValueOps("rrr").set("yyyyyyyy");
       // System.out.println("============="+redisTemplate.boundValueOps("rrr").get());//
        //jedisClusterConfig.getJedisCluster().set("bbb","bbbbbbbbbb");
       // System.out.println(jedisClusterConfig.getJedisCluster().get("bbb"));
        redisClient.set("zzz","ddddddddddd");
        System.out.println("++++++++++++++++++++"+redisClient.get("zzz"));
    }
}
