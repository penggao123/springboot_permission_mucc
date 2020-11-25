package com.mmall.common;

import com.mmall.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName HttpInterceptor
 * @Description TODO http请求前后监听
 * @Author gaopeng
 * @Date 2020/11/14 13:42
 * @Version 1.0
 **/
@Configuration
public class HttpInterceptor extends HandlerInterceptorAdapter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String START_TIME = "requestStartTime";


    /**
      * 请求之前的方法
      **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求的url
        String url = request.getRequestURL().toString();
        Map<String, String[]> parameterMap = request.getParameterMap();//获取请求参数
        logger.info("request start. url:{} , params:{}",url, JsonMapper.obj2String(parameterMap));

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        return true;//请求之前都给他返回true
    }

    /**
     * 请求正常结束执行的方法
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取请求的url
        String url = request.getRequestURL().toString();
        long startTime = (long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        logger.info("request finished. url:{}, cost:{}", url,endTime - startTime);
        removeThreadLocalInfo();
    }

    /**
      * @Author gaopeng
      * @Description //  请求之后执行的方法(任何情况下都会调用(包括异常、正常、错误))
      * @Date  2020/11/14
      * @param
      * @return void
      **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String url = request.getRequestURL().toString();
        long startTime = (long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        logger.info("request completed. url:{}, cost:{}", url, endTime - startTime);
        removeThreadLocalInfo();
    }


    /**
     * 使用ThreadLocal移除信息
     */
    public void removeThreadLocalInfo() {
        RequestHolder.remove();
    }
}
