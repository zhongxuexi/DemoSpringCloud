package com.jess.service.impl;

import com.github.pagehelper.PageHelper;
import com.jess.dao.IMemberDao;
import com.jess.entity.Member;
import com.jess.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Override
    public Integer insertMember(Member member) {
        if (member==null){
            Member memberVo=null;
            System.out.println("----------------------插入第一个对象");
            //try{
                memberVo = new Member("顶顶顶666", "男", 30);
                iMemberDao.insertMember(memberVo);
                //System.out.println("----------------------插入第二个对象");
                //memberVo = new Member("灌灌灌灌666", "男", 400000);
                //iMemberDao.insertMember(memberVo);
            //}catch (Exception e){
                //System.out.println("发生异常--------------");
                //throw new Exception("发生异常--------------"+memberVo.toString());
            //}
        }else{
            iMemberDao.insertMember(member);
        }
        return 0;
    }
}
