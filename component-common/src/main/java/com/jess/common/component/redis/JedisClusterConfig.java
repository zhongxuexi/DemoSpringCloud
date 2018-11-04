//package com.jess.common.component.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @Auther: zhongxuexi
// * @Date: 2018/11/4 19:18
// * @Description:
// */
//@Configuration
//public class JedisClusterConfig {
//    @Value("${redis.clusterNodes}")
//    private String clusterNodes;
//    @Value("${redis.commandTimeout}")
//    private int commandTimeout;
//    // 连接池最大阻塞等待时间（使用负值表示没有限制）
//    @Value("${redis.pool.max-wait}")
//    private int maxWaitMillis;
//    // 连接池中的最大空闲连接
//    @Value("${redis.pool.max-idle}")
//    private int maxIdle;
//
//    @Bean
//    public JedisCluster getJedisCluster() {
//        String[] serverArray = clusterNodes.split(",");
//        Set<HostAndPort> nodes = new HashSet<>();
//
//        for (String ipPort : serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//        }
//        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        return new JedisCluster(nodes, commandTimeout,jedisPoolConfig);
//    }
//}
