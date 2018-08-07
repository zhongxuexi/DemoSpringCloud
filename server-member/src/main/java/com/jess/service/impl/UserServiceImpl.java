package com.jess.service.impl;

import com.github.pagehelper.PageHelper;
import com.jess.dao.extend.UserExtendMapper;
import com.jess.dao.msg.UserMapper;
import com.jess.entity.User;
import com.jess.service.UserService;
import com.jess.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserExtendMapper userExtendMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean<User> findByPage(String keyword, int currentPage, int pageSize) throws Exception {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        List<User> list = userExtendMapper.getAll(keyword);            //获取所有数据
        int countNums = userExtendMapper.totalCount(keyword);          //总记录数
        PageBean<User> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(list);
        return pageData;
    }

    @Transactional
    @Override
    public Integer addUser(User user) {
        if (user == null) {
            return 0;
        }
        return userMapper.insertSelective(user);
    }

    @Transactional
    @Override
    public Integer updateUser(User user) throws Exception {
        if (user == null) {
            return 0;
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    @Override
    public Integer deleteUser(Long id) throws Exception {
        int a=5/0;
        User user = new User();
        user.setId(id);
        user.setDeleteState("1");
        user.setUpdateTime(new Date());
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;
    }

    @Transactional
    @Override
    public Integer batchDelete(String ids) throws Exception {
        int count = 0;
        if(ids.length()>0){
            String[] idArray = ids.split(",");
            if (idArray != null && idArray.length > 0) {
                for (String id : idArray) {
                    if (deleteUser(Long.parseLong(id)) > 0) {
                        count++;
                    } else {
                        return 0;
                    }

                }
            }
        }

        return count;
    }

    @Override
    public User findUserById(Long id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findUserByName(String userName) throws Exception {
        Example example = new Example(User.class);
        example.or().andLike("userName","%" + userName + "%");
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> findUserByAge(Byte age) throws Exception {
        Example example = new Example(User.class);
        example.or().andEqualTo("age",age);
        return userMapper.selectByExample(example);
    }
}
