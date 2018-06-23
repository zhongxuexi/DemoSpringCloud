package com.jess.service;

import com.jess.entity.Member;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
public interface IMemberService{
    /**
     *
     * @return
     */
    List<Member> getAll() throws Exception;
}
