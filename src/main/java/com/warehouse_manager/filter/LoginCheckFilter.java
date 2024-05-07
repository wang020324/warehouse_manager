package com.warehouse_manager.filter;

import com.alibaba.fastjson.JSON;
import com.warehouse_manager.pojo.user.Result;
import com.warehouse_manager.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/*
* 定义一个过滤器实现登录限制*/
public class LoginCheckFilter implements Filter {
    //自动注入的前提是出现上面有注解
    //@Autowired
    private StringRedisTemplate redisTemplate;
    public void setRedisTemplate(StringRedisTemplate redisTemplate){
        this.redisTemplate =redisTemplate;
    }
    //过滤器拦截到请求执行的方法
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) req;
        HttpServletResponse response =(HttpServletResponse)resp;
        //1.白名单请求:直接放行
        //创建List接口，里面放白名单url接口
        List<String> urlList=new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        urlList.add("/logout");
        urlList.add("/product/img-upload");//上传图片接口

        //过滤器拦截到的当前请求的url接口
        String url =request.getServletPath();
        if(urlList.contains(url)||url.contains("/img/upload"))
        {
            //加上后面是为了放行所有的图片url
            //白名单请求直接放行
            chain.doFilter(request,response);
            return;
        }

        //2.其他请求都校验是否携带token，以及判断redis中是否存在token文件
        String token =request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        //1>.有说明已经登录，放行
        if(StringUtils.hasText(token)&&redisTemplate.hasKey(token)){
            chain.doFilter(request,response);
            return;

        }
        // 2>.没有则未登录/登录过期，则请求不放行，并且给前端做出响应


        Result result=Result.err(Result.CODE_ERR_UNLOGINED,"尚未登录");
        String jsonStr =JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
        out.close();

    }
}
