package com.jess.member;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/11/3 17:26
 * @Description:
 */
public class Test3 {
    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void test31(){
        System.out.println(jedisCluster.get("zhong"));
    }
}
