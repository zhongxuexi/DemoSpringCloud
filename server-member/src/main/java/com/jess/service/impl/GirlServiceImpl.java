package com.jess.service.impl;

import com.jess.common.util.Result;
import com.jess.common.config.multipleDB.DS;
import com.jess.dao.GirlMapper;
import com.jess.entity.Girl;
import com.jess.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/8/28.
 */
@Service
public class GirlServiceImpl implements GirlService{
    @Autowired
    private GirlMapper girlMapper;

    //使用数据源2查询
    @DS("datasource2")
    @Override
    public Result findByName(String name) {
        Example example = new Example(Girl.class);
        example.or().andLike("name","%" + name + "%");
        List<Girl> girls = girlMapper.selectByExample(example);
        return Result.success(girls,girls.size());
    }
}
