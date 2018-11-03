package com.jess.member.service;

import com.jess.common.util.Result;
import com.jess.member.entity.Girl;

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

    /**
     * 通过主键id查找
     * @param id
     * @return
     */
    Girl findById(int id);
}
