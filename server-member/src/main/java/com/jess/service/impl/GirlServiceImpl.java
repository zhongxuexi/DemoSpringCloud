package com.jess.service.impl;

import com.google.common.collect.Maps;
import com.jess.common.config.db.DataSourceManager;
import com.jess.common.constants.DataSourceConstant;
import com.jess.common.util.Result;
import com.jess.dao.GirlMapper;
import com.jess.dao.UserMapper;
import com.jess.entity.Girl;
import com.jess.entity.User;
import com.jess.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/8/28.
 */
@Service
public class GirlServiceImpl implements GirlService{
    @Autowired
    private GirlMapper girlMapper;
    @Autowired
    private UserMapper userMapper;

    //使用数据源default查询
    //@DS(DataSourceConstant.DB_MASTER)
    @Override
    public Result findByName(String name) {
        //DataSourceContextHolder.setDB(DataSourceConstant.DB_MASTER);
        Example example1 = new Example(Girl.class);
        example1.or().andLike("name","%" + name + "%");
        List<Girl> girls = DataSourceManager.getDataSourceMapper(girlMapper,DataSourceConstant.DB_MASTER).selectByExample(example1);
        //切换数据源
        //DataSourceContextHolder.setDB(DataSourceConstant.DB_DEFAULT);
        Example example = new Example(User.class);
        example.or().andLike("username","%" + name + "%").andCondition("delete_state=0");
        List<User> users = DataSourceManager.getDataSourceMapper(userMapper,DataSourceConstant.DB_DEFAULT).selectByExample(example);
        Map map = Maps.newLinkedHashMap();
        map.put("girls",girls);
        map.put("users",users);
        return Result.success(map,girls.size()+users.size());
    }
}
