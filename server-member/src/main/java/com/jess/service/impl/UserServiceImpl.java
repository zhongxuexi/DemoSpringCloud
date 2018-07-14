package com.jess.service.impl;

import com.github.pagehelper.PageHelper;
import com.jess.dao.extend.UserExtendMapper;
import com.jess.entity.User;
import com.jess.service.UserService;
import com.jess.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserExtendMapper userExtendMapper;
    @Override
    public List<User> getAllByPage(String keyword, int currentPage, int pageSize) throws Exception{
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        List<User> list = userExtendMapper.getAll(keyword);            //获取所有数据
        int countNums = userExtendMapper.totalCount(keyword);               //总记录数
        PageBean<User> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(list);
        return pageData.getItems();
    }
    @Transactional
    @Override
    public Integer insertUser(User user) {
        if (user==null){
            User userVo=null;
            System.out.println("----------------------插入第一个对象");
            //try{
                userVo = new User();
                userExtendMapper.insertUser(userVo);
                //System.out.println("----------------------插入第二个对象");
                //memberVo = new Member("灌灌灌灌666", "男", 400000);
                //iMemberDao.insertMember(memberVo);
            //}catch (Exception e){
                //System.out.println("发生异常--------------");
                //throw new Exception("发生异常--------------"+memberVo.toString());
            //}
        }else{
            userExtendMapper.insertUser(user);
        }
        return 0;
    }
}
