package com.jess.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jess.commons.api.service.FieldMapApi;
import com.jess.commons.api.service.RedisServiceApi;
import com.jess.commons.util.PageBean;
import com.jess.dao.UserMapper;
import com.jess.dao.extend.UserExtendMapper;
import com.jess.commons.entity.User;
import com.jess.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserExtendMapper userExtendMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisServiceApi redisServiceApi;
    @Autowired
    private FieldMapApi fieldMapApi;

    @Override
    public PageBean<User> findByPage(String keyword, int currentPage, int pageSize) throws Exception {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        StringBuffer sb = new StringBuffer("SELECT a.* FROM t_user a where 1=1 and a.delete_state = '0' ");
        if(StringUtils.isNoneBlank(keyword)){
            sb.append("and (");
            sb.append("a.realname like CONCAT('%',#{keyword},'%') or");
            sb.append("a.username like CONCAT('%',#{keyword},'%') or");
            sb.append("a.education like CONCAT('%',#{keyword},'%') or");
            sb.append("a.nativeplace like CONCAT('%',#{keyword},'%') or");
            sb.append("a.address like CONCAT('%',#{keyword},'%')");
            sb.append(")");
            sb.append("ORDER BY a.create_time desc limit "+currentPage+","+pageSize);
        }else{
            sb.append("ORDER BY a.create_time desc limit "+currentPage+","+pageSize);
        }
        String sql = sb.toString();
        List<User> list=null;
        if(redisServiceApi.getList(sql,User.class)!=null&&redisServiceApi.getList(sql,User.class).size()>0){
            list = redisServiceApi.getList(sql, User.class);    //缓存取数据
        }else{
            list = userExtendMapper.getAll(keyword);     //db取数据
            redisServiceApi.setList(sql,list);  //存缓存
        }
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
    public Map<String,Object> findUserById(Long id,String field) throws Exception {
        User user=null;
        String[] fields = fieldMapApi.getFields(field,User.class);
        String sql = "select "+field+" from t_user where id="+id;
        if(redisServiceApi.get(sql,User.class)!=null){
            user = redisServiceApi.get(sql, User.class);    //取缓存
        }else {
            Example example = new Example(User.class);
            example.or().andEqualTo("id",id);
            example.selectProperties(fields);
            user = userMapper.selectOneByExample(example);  //db取数据
            redisServiceApi.set(sql,user,24*60*60);//存入缓存
        }
        Map resultMap = fieldMapApi.getResultMap(fields, user);
        return resultMap;
    }

    @Override
    public List<Map<String,Object>> findUserByName(String userName,String field) throws Exception {
        Example example = new Example(User.class);
        example.or().andLike("username","%" + userName + "%").andCondition("delete_state=0");
        String[] fields = fieldMapApi.getFields(field,User.class);
        example.selectProperties(fields);
        List<User> users = userMapper.selectByExample(example);
        List<Map<String, Object>> resultMap = fieldMapApi.getResultMap(fields, users);
        return resultMap;
    }

    @Override
    public List<User> findUserByAge(Byte age) throws Exception {
        Example example = new Example(User.class);
        example.or().andEqualTo("age",age);
        return userMapper.selectByExample(example);
    }
}
