package com.jess.order.dao.extend;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/8 18:06
 * @Description:
 */
public interface VersionExtendMapper {
    /**
     * 简单查询，返回Map结果集
     * @return
     */
    List<Map<String, Object>> find();
}
