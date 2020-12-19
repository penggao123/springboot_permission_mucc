package com.mmall.config;

import com.mmall.filter.AclControlFilter;
import com.mmall.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 使用FilterRegistrationBean 注册自定义filter
 */
@Configuration
public class FilterConfig {

    private final static String noAuthUrl = "/sys/user/noAuth.page";//无权限页面

    /**
     * 注册请求拦截
     * @return
     */
    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new LoginFilter());
        filterRegistrationBean.addUrlPatterns("/sys/*","/admin/*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }



    /**
     *  注册权限拦截的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean registAclFilterBean(){
//        /sys/user/noAuth.page,/login.page
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new AclControlFilter());
        bean.addUrlPatterns("/login.page", noAuthUrl);
        bean.addUrlPatterns("/sys/*","/admin/*");
        bean.setOrder(2);
        return bean;
    }




}
