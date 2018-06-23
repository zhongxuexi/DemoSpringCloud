package com.jess.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhongxuexi on 2018/6/6.
 */
@Component
public class TokenPreZullFilter extends ZuulFilter {

    @Override
    public String filterType(){
        return "pre";
    }
    @Override
    public int filterOrder(){
        return 1;
    }
    @Override
    public boolean shouldFilter(){
        return true;
    }
    @Override
    public Object run(){
        RequestContext ctx= RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();
        System.out.println(String.format("%s request to %s",request.getMethod(),request.getRequestURL().toString()));
        String token=request.getParameter("token");
        System.out.println("token:"+token);
        if(token==null){
        //认证失败
            System.out.println("token验证失败");
            HttpServletResponse response=ctx.getResponse();
            response.setCharacterEncoding("utf-8");//设置字符集
            response.setContentType("text/html;charset=utf-8");//设置相应格式
            response.setStatus(401);
            ctx.setSendZuulResponse(false);//不进行路由
            try{
                response.getWriter().write("token验证失败");//响应体

            }catch(IOException e){
                System.out.println("responseio异常");
                e.printStackTrace();
            }
            ctx.setResponse(response);
            return null;
        }
        System.out.println("token验证成功");
        return null;

    }

}
