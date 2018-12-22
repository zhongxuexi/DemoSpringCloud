package com.jess.member.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.jess.common.component.redis.RedisKeys;
import com.jess.common.util.ObjectToMapUtil;
//import com.jess.common.component.redis.RedisClient;
import com.jess.common.util.Result;
import com.jess.member.dao.UserMapper;
import com.jess.member.dao.extend.UserExtendMapper;
import com.jess.member.entity.User;
import com.jess.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Service
@CacheConfig(cacheNames = RedisKeys._CACHE_SHORT)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserExtendMapper userExtendMapper;
    @Autowired
    private UserMapper userMapper;
    // @Autowired
    //private RedisClient redisClient;

    @Override
    public Result findByPage(String keyword, int currentPage, int pageSize) throws Exception {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<User> list = userExtendMapper.getAll(keyword);     //db取数据
        long countNums = page.getTotal();      //总记录数
        return Result.success(list, (int) countNums);
    }

    @Transactional
    @CacheEvict(value = RedisKeys._CACHE_COMMON)
    @Override
    public Integer addUser(User user) {
        if (user == null) {
            return 0;
        }
        return userMapper.insertSelective(user);
    }

    @Transactional
    @CacheEvict(value = RedisKeys._CACHE_COMMON, allEntries = true)
    @Override
    public Integer updateUser(User user) throws Exception {
        if (user == null) {
            return 0;
        }
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;
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
        if (ids.length() > 0) {
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

    @Cacheable(value = RedisKeys._CACHE_COMMON)
    @Override
    public Map<String, Object> findUserById(Long id, String field) throws Exception {
        Example example = new Example(User.class);
        example.or().andEqualTo("id", id);
        User user = userMapper.selectOneByExample(example);  //db取数据
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put(field, user);
        return ObjectToMapUtil.getCombinationMap(map);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Cacheable(key = "#root.targetClass.name+'.'+#root.method.name+'_'+#userName+','+#field")
    @Override
    public List<Map<String, Object>> findUserByName(String userName, String field) throws Exception {
        Example example = new Example(User.class);
        example.or().andLike("username", "%" + userName + "%").andCondition("delete_state=0");
        List<User> users = userMapper.selectByExample(example);
        return ObjectToMapUtil.getInstance().getCombinationMapList(field, users);
    }

    @Cacheable(key = "#root.targetClass.name+'.'+#root.method.name+'_'+#age", unless = "#result==null")
    @Override
    public List<User> findUserByAge(Byte age) throws Exception {
        Example example = new Example(User.class);
        example.or().andEqualTo("age", age).andCondition("delete_state=0");
        return userMapper.selectByExample(example);
    }
}
