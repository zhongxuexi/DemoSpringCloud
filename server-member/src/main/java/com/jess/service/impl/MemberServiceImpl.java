package com.jess.service.impl;

import com.github.pagehelper.PageHelper;
import com.jess.dao.IMemberDao;
import com.jess.entity.Member;
import com.jess.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberDao iMemberDao;
    @Override
    public List<Member> getAll() throws Exception{
        //PageHelper.startPage(1,4);
        return iMemberDao.getAll();
    }
}
