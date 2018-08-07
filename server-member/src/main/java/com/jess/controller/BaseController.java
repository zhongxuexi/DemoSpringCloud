package com.jess.controller;

import com.jess.commons.util.CodeMsg;
import com.jess.commons.util.Result;

import java.util.List;

/**
 * 基础 Controller
 * Created by zhongxuexi on 2018/7/15.
 */
public abstract class BaseController<T> {
    /**
     * 增删改获取数据结果
     * @param count
     * @return
     */
    public static Result getResult(Integer count) {
        if (count ==0){
            return Result.error(CodeMsg.SERVER_EXCEPTION);
        }
        return Result.success();
    }
    /**
     * 获取查询的数据结果--单个
     * @param t
     * @return
     */
    public static  <T> Result<T> getResult(T t) {
        if (t == null){
            return Result.error(CodeMsg.SERVER_EXCEPTION);
        }
        return Result.success((T) t,1);
    }

    /**
     * 获取查询的数据结果--多个
     * @param t
     * @return
     */
    public static  <T> Result<T> getResult(List<T> t) {
        if (t == null || t.size() == 0){
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        return Result.success((T) t,t.size());
    }
}
