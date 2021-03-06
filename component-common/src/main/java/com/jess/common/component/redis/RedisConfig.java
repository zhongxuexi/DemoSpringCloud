package com.jess.common.component.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.timeout}")
    private int timeout;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.data-timeout}")
    private int dataTimeout;

    /**
     * 在使用@Cacheable时，如果不指定key，则使用找个默认的key生成器生成的key
     * <p> Title: 生成key </p>
     * <p>Description: key组成： 类名+方法名+参数值</p>
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

    /**
     * <p>Title: 配置连接参数</p>
     * <p>Description: 配置ip/主机名、端口、密码、连接超时时间</p>
     * @return
     */
    @Bean(name = "redisConnectionFactory")
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setTimeout(timeout); // 设置连接超时时间
        return factory;
    }

    /**
     * <p>Title: 设置序列化工具，开启事务</p>
     * <p> Description: 这样ReportBean不需要实现Serializable</p>
     * @param redisConnectionFactory
     * @return RedisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(@Qualifier(value = "redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.setEnableTransactionSupport(true);// 开启事务支持 ,在方法或者类上统一使用@Transactional标注事务
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * <p>Title: 设置数据缓存默认超时时长</p>
     * <p>Description: 以秒为单位 </p>
     * @param redisTemplate
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(@Qualifier(value = "redisTemplate") RedisTemplate redisTemplate,RedisKeys redisKeys) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // 设置缓存默认过期时间（全局的）
        cacheManager.setDefaultExpiration(dataTimeout);

        // 根据key设定具体的缓存时间，key统一放在常量类RedisKeys中
        cacheManager.setExpires(redisKeys.getExpiresMap());

        List<String> cacheNames = new ArrayList<String>(redisKeys.getExpiresMap().keySet());
        cacheManager.setCacheNames(cacheNames);
        return cacheManager;
    }

    @SuppressWarnings("unchecked")
    private void setSerializer(StringRedisTemplate template) {
        @SuppressWarnings("rawtypes")
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

}