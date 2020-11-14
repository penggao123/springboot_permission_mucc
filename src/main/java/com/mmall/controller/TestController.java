package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.exception.ParamException;
import com.mmall.param.TestVo;
import com.mmall.utils.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 22:55
 * @Version 1.0
 **/

@RestController
@RequestMapping("/test")
//@Slf4j
public class TestController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/hello.json")
    public JsonData hello(){
        return JsonData.success("hello ");

    }


    @GetMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo testVo) throws ParamException{
        BeanValidator.check(testVo);

        return JsonData.success("test validate");

    }
}
