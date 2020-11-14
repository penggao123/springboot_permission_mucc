package com.mmall.config;

import com.mmall.common.SpringExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @ClassName SpringConfiguration
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 22:38
 * @Version 1.0
 **/

@Configuration
public class SpringConfiguration {


    @Bean
    public SpringExceptionResolver resolver(){
        return new SpringExceptionResolver();
    }

    @Bean
    public MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }
}
