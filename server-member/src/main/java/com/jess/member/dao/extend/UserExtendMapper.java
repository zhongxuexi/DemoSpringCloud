package com.jess.member.dao.extend;


import com.jess.member.entity.User;
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

}
