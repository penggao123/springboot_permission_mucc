package com.mmall.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationContextHelper
 * @Description 获取spring上下文中的bean
 * @Author gaopeng
 * @Date 2020/11/14 12:53
 * @Version 1.0
 **/
@Component
public class ApplicationContextHelper {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) throws BeansException{
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> clazz) throws Exception{
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T popBean(String name, Class<T> clazz) {

        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name,clazz );
    }


}
