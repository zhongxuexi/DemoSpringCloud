package com.jess.fallback;

import com.jess.service.MemberFegin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@Component
public class MemberFallBack implements MemberFegin{
    @Override
    public List<Object> getAll() throws Exception {
        List<Object> list = new ArrayList<Object>();
        list.add("报告jess，服务器发生异常了。。。");
        return list;
    }
}
