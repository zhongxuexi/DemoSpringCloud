package com.jess.dao.extend;

import com.jess.commons.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
public interface UserExtendMapper{
    /**
     *查询所有
     * @return
     */
    List<User> getAll(@Param("keyword") String keyword);

    /**
     * 获取总记录数
     * @return
     */
    int totalCount(@Param("keyword") String keyword);

}
