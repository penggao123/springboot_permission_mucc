package com.mmall.filter;

import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.RequestHolder;
import com.mmall.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 权限点过滤器(用于拦截请求是否有权限访问)
 */
public class AclControlFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 执行的方法
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException , Exception{
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;
        //获取请求url
        String contextPath = servletRequest.getContextPath();
        //请求参数
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        FilterRegistrationBean filterRegistrationBean = ApplicationContextHelper.popBean(FilterRegistrationBean.class);
        Collection patterns = filterRegistrationBean.getUrlPatterns();
        if (patterns.contains(contextPath)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        //获取用户
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            //用户不存在，提示用户不存在

        }

        //校验

    }

    /**
     * 销毁方法
     */
    @Override
    public void destroy() {

    }
}
