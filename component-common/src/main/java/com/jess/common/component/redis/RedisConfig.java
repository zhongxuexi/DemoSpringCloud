package com.jess.common.component.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jess.common.util.LogUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * <p>ClassName: RedisConfig</p>
 * <p>Description: Redis缓存配置</p>
 * <p> Author: jess.zhong </p>
 * <p> Date: 2018年8月1日</p>
 */
@Data
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${redis.cluster.commandTimeout}")
    private int commandTimeout;
    @Value("${redis.cluster.max-attempts}")
    private int maxAttempts;
    @Value("${redis.cluster.max-redirects}")
    private int maxRedirects;
    // 连接池最大连接数（使用负值表示没有限制）
    @Value("${redis.cluster.max-active}")
    private int maxTotal;
    // 连接池最大阻塞等待时间（使用负值表示没有限制）
    @Value("${redis.cluster.max-wait}")
    private int maxWaitMillis;
    // 连接池中的最大空闲连接
    @Value("${redis.cluster.max-idle}")
    private int maxIdle;
    // 连接池中的最小空闲连接
    @Value("${redis.cluster.min-idle}")
    private int minIdle;
    @Value("${redis.cluster.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${redis.cluster.nodes}")
    private String clusterNodes;

    /**
     * 在使用@Cacheable时，如果不指定key，则使用找个默认的key生成器生成的key
     * <p> Title: 生成key </p>
     * <p>Description: key组成： 类名+方法名+参数值</p>
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".").append(method.getName());
            StringBuilder paramsSb = new StringBuilder();
            for (Object param : params) {
                // 如果不指定，默认生成包含到键值中
                if (param != null) {
                    paramsSb.append(param.toString());
                }
            }
            if (paramsSb.length() > 0) {
                sb.append("_").append(paramsSb);
            }
            return sb.toString();
        };
    }

    @Bean(name = "jedisClusterConfig")
    public RedisClusterConfiguration getClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", commandTimeout);
        source.put("spring.redis.cluster.max-redirects", maxRedirects);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    /**
     * 配置JedisPoolConfig
     *
     * @return JedisPoolConfig实体
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        LogUtil.logger.info("=== RedisConf jedisPoolConfig 初始化 ===");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 连接池最大连接数（使用负值表示没有限制）
        jedisPoolConfig.setMaxTotal(this.maxTotal);
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
        // 连接池中的最大空闲连接
        jedisPoolConfig.setMaxIdle(this.maxIdle);
        // 连接池中的最小空闲连接
        jedisPoolConfig.setMinIdle(this.minIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        // jedisPoolConfig.setTestOnCreate(true);
        // jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    /**
     * <p>Title: 配置连接参数</p>
     * <p>Description: 配置ip/主机名、端口、密码、连接超时时间</p>
     *
     * @return
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory redisConnectionFactory(@Qualifier(value = "jedisClusterConfig") RedisClusterConfiguration redisClusterConfiguration,
                                                         @Qualifier("jedisPoolConfig") JedisPoolConfig poolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfiguration);
        factory.setPoolConfig(poolConfig);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
//        factory.setHostName(host);
//        factory.setPort(port);
//        factory.setPassword(password);
//        factory.setDatabase(database);
//        factory.setTimeout(timeout); // 设置连接超时时间
        return factory;
    }

    /**
     * <p>Title: 设置序列化工具，开启事务</p>
     * <p> Description: 这样ReportBean不需要实现Serializable</p>
     *
     * @param redisConnectionFactory
     * @return RedisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setEnableTransactionSupport(true);// 开启事务支持 ,在方法或者类上统一使用@Transactional标注事务
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    @SuppressWarnings("unchecked")
    private void setSerializer(RedisTemplate template) {
        @SuppressWarnings("rawtypes")
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new EntityRedisSerializer());
//        template.setValueSerializer(new EntityRedisSerializer());
    }

    /**
     * <p>Title: 设置数据缓存默认超时时长</p>
     * <p>Description: 以秒为单位 </p>
     *
     * @param redisTemplate
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes")@Qualifier(value = "redisTemplate") RedisTemplate redisTemplate, RedisKeys redisKeys) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // 设置缓存默认过期时间（全局的）
        cacheManager.setDefaultExpiration(commandTimeout);

        // 根据key设定具体的缓存时间，key统一放在常量类RedisKeys中
        cacheManager.setExpires(redisKeys.getExpiresMap());

        List<String> cacheNames = new ArrayList<String>(redisKeys.getExpiresMap().keySet());
        cacheManager.setCacheNames(cacheNames);
        return cacheManager;
    }

//    @Bean(name = "jedisCluster")
//    public JedisCluster getJedisCluster() {
//        // 截取集群节点
//        String[] cluster = clusterNodes.split(",");
//        // 创建set集合
//        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//        // 循环数组把集群节点添加到set集合中
//        for (String node : cluster) {
//            String[] host = node.split(":");
//            //添加集群节点
//            nodes.add(new HostAndPort(host[0], Integer.parseInt(host[1])));
//        }
//        JedisCluster jc = new JedisCluster(nodes);
//        return jc;
//
//    }

}