package com.jess.service;

import com.jess.common.util.Result;

/**
 * Created by zhongxuexi on 2018/8/28.
 */
public interface GirlService {
    /**
     * 通过名字查询
     * @param name
     * @return
     */
    Result findByName(String name);
}
