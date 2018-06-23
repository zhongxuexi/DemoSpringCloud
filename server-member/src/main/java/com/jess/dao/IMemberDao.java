package com.jess.dao;

import com.jess.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Mapper
public interface IMemberDao {
    /**
     *
     * @return
     */
    List<Member> getAll();
}
