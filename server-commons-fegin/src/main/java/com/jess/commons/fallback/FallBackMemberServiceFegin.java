package com.jess.commons.fallback;

import com.jess.commons.service.MemberServiceFegin;
import org.springframework.stereotype.Component;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@Component
public class FallBackMemberServiceFegin implements MemberServiceFegin {
    @Override
    public String test() throws Exception {
        return "报告jess，服务器发生异常了。。。";
    }
}
