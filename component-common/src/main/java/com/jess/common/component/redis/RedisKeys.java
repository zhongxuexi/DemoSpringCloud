package com.jess.common.component.redis;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/8 14:10
 * @Description: 方法缓存key常量
 */
@Component
public class RedisKeys {

    public static final String _CACHE_COMMON = "_cache_common";// 缓存key--common
    private static final Long _CACHE_COMMON_SECOND = 24*60*60L;// 缓存时间(秒)--common

    public static final String _CACHE_SHORT = "_cache_short";// 缓存key-short
    private static final Long _CACHE_SHORT_SECOND = 60L;// 缓存时间(秒)--short

    // 根据key设定具体的缓存时间
    private Map<String, Long> expiresMap = null;

    @PostConstruct
    public void init(){
        expiresMap = new HashMap<>();
        expiresMap.put(_CACHE_COMMON, _CACHE_COMMON_SECOND);
        expiresMap.put(_CACHE_SHORT, _CACHE_SHORT_SECOND);
    }

    public Map<String, Long> getExpiresMap(){
        return this.expiresMap;
    }
}
