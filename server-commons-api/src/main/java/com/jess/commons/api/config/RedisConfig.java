package com.jess.commons.api.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: RedisConfig</p>
 * <p>Description: Redis缓存配置</p>
 * <p> Author: jess.zhong </p>
 * <p> Date: 2018年8月1日</p>
 */
@Data
@Configuration
@EnableCaching
public class RedisConfig {
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
	 * <p> Title: 生成key </p>
	 * <p>Description: key组成： 类名+方法名+参数值</p>
	 * @return
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}

	/**
	 * <p>Title: 配置连接参数</p>
	 * <p>Description: 配置ip/主机名、端口、密码、连接超时时间</p>
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(host);
		factory.setPort(port);
		factory.setPassword(password);
		factory.setTimeout(timeout); // 设置连接超时时间
		return factory;
	}

	/**
	 * <p>Title: 设置数据缓存默认超时时长</p> 
	 * <p>Description: 以秒为单位 </p>
	 * @param redisTemplate
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(dataTimeout);
		return cacheManager;
	}

	/**
	 * <p>Title: 设置序列化工具，开启事务</p>
	 * <p> Description: 这样ReportBean不需要实现Serializable</p>
	 * @param redisConnectionFactory
	 * @return RedisTemplate
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
		template.setEnableTransactionSupport(true);// 开启事务支持 ,在方法或者类上统一使用@Transactional标注事务
		setSerializer(template);
		template.afterPropertiesSet();
		return template;
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