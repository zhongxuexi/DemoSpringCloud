package com.jess.common.config.commonDB;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/8/29 21:31
 * @Description:注解定义
 */
//自定义注解
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DS {
    String value() default "default";
}
