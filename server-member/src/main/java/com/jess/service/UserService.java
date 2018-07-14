package com.jess.service;

import com.jess.entity.User;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
public interface UserService {
    /**
     *
     * @return
     */
    List<User> getAllByPage(String keyword, int currentPage, int pageSize) throws Exception;

    /**
     * 插入用户
     * @param user
     */
    Integer insertUser(User user);
}
