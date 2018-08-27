package com.jess.common.fallback;

import com.jess.common.service.MemberServiceFegin;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@Component
public class MemberServiceFeginFallBack implements MemberServiceFegin {
    @Override
    public String test(@RequestParam("desc") String desc) throws Exception {
        return "报告jess，服务器发生异常了。。。";
    }
}
