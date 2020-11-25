package com.mmall.common;

import com.mmall.exception.ParamException;
import com.mmall.exception.PerssionException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.Node;

/**
 * @ClassName SpringExceptionResolver
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 22:10
 * @Version 1.0
 **/


public class SpringExceptionResolver implements HandlerExceptionResolver{
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        //获取请求的url
        String url = request.getRequestURL().toString();
        ModelAndView mv;

        //定义默认异常消息
        String defaultMsg = "System error";

        //这里我们要求项目中的所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")){
            if (ex instanceof PerssionException || ex instanceof ParamException){
                JsonData result = JsonData.fail(ex.getMessage());
                System.out.println(ex);
                mv = new ModelAndView("jsonView",result.toMap());
            }else {
                JsonData result = JsonData.fail(defaultMsg);
                System.out.println(ex);
                mv = new ModelAndView("jsonView", result.toMap());
            }

        }else if (url.endsWith(".page")){//所有请求page页面，都使用.page结尾
            JsonData result = JsonData.fail(defaultMsg);
            System.out.println(ex);
            mv = new ModelAndView("exception", result.toMap());

        }else {
            JsonData result = JsonData.fail(defaultMsg);
            System.out.println(ex);
            mv = new ModelAndView("jsonView",result.toMap());
        }

        return mv;
    }
}






































