package com.jess.common.component.redis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: redis缓存key批量删除
 * Author: zhongxuexi
 * Date: 2018/11/23 17:01
 */
@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {
    String[] key(); //key数组
}
