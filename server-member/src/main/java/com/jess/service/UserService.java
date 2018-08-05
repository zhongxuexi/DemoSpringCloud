package com.jess.service;

import com.jess.entity.User;
import com.jess.util.PageBean;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
public interface UserService {
    /**
     * 条件查询+分页
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    PageBean<User> findByPage(String keyword, int currentPage, int pageSize) throws Exception;

    /**
     * 新增用户
     * @param user
     * @return
     * @throws Exception
     */
    Integer addUser(User user)  throws Exception;

    /**
     * 修改用户
     * @param user
     * @return
     * @throws Exception
     */
    Integer updateUser(User user) throws Exception;

    /**
     * 通过主键ID删除用户
     * @param id
     * @return
     * @throws Exception
     */
    Integer deleteUser(Long id) throws Exception;

    /**
     * 通过主键ID批量删除用户
     * @param ids
     * @return
     * @throws Exception
     */
    Integer batchDelete(String ids) throws Exception;

    /**
     * 根据用户主键ID查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    User findUserById(Long id) throws Exception;

    /**
     * 根据用户名字模糊查询用户信息
     * @param userName
     * @return
     * @throws Exception
     */
    List<User> findUserByName(String userName) throws Exception;

    /**
     * 根据用户年龄查询用户信息
     * @param age
     * @return
     * @throws Exception
     */
    List<User> findUserByAge(Byte age) throws Exception;
}
