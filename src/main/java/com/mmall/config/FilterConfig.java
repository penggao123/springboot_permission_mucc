package com.mmall.config;

import com.mmall.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用FilterRegistrationBean 注册自定义filter
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new LoginFilter());
        filterRegistrationBean.addUrlPatterns("/sys/*","/admin/*");
        return filterRegistrationBean;
    }

}
