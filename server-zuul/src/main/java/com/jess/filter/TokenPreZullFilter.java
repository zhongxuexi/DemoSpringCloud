package com.jess.filter;

import com.google.common.collect.Maps;
import com.jess.common.util.HttpUtils;
import com.jess.common.util.LogUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@Component
public class TokenPreZullFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LogUtil.logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        Map<String, Object> requestMap = Maps.newConcurrentMap();
//        try {
//            requestMap = HttpUtils.getRequestMap(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Object sessionid=requestMap.get("sessionid");
        if(!org.springframework.util.ObjectUtils.isEmpty(sessionid)){
            String sessionidStr = requestMap.get("sessionid").toString();
            System.out.println("获取sessionid-->" + sessionidStr);
        }
        String token = request.getParameter("token");
        LogUtil.logger.info("token:" + token);
        if (StringUtils.isNoneBlank(token)) {
            if ("123".equals(token)) {
                LogUtil.logger.info("token验证成功!");
            } else {
                //认证失败
                LogUtil.logger.info("token验证失败");
                HttpServletResponse response = ctx.getResponse();
                response.setCharacterEncoding("utf-8");//设置字符集
                response.setContentType("text/html;charset=utf-8");//设置相应格式
                response.setStatus(401);
                ctx.setSendZuulResponse(false);//不进行路由
                try {
                    response.getWriter().write("token验证失败");//响应体
                } catch (IOException e) {
                    LogUtil.logger.info("responseio异常");
                    e.printStackTrace();
                }
                ctx.setResponse(response);
            }
        }
        return null;
    }
}
