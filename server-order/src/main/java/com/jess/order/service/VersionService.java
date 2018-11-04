package com.jess.order.service;

import com.jess.order.entity.Version;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/8 18:08
 * @Description:
 */
public interface VersionService {
    /**
     * 简单查询，返回Map结果集
     * @return
     */
    List<Map<String, Object>> find();

    /**
     * 通过主键id查找
     * @param id
     * @return
     */
    Version findById(Long id);
}
