package com.jess.commons.api.mapper;

import tk.mybatis.mapper.common.Mapper;

import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by zhongxuexi on 2018/8/5.
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
