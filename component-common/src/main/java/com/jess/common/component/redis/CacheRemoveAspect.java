package com.jess.common.component.redis;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Description: redis缓存key批量模糊删除AOP
 * Author: zhongxuexi
 * Date: 2018/11/23 17:04
 */
@Aspect
@Component
public class CacheRemoveAspect {
    @Autowired
    private RedisClient redisClient;

    @Before("execution(* com.jess.*.service..*(..))")
    private void process(JoinPoint joinPoint) {
        //切面逻辑
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);
        if (cacheRemove != null) {
            String[] keys = cacheRemove.key(); //需要移除的key
            for (String key : keys) {
                redisClient.deleteFuzzy(key);
            }
        }
    }
}
