package com.mmall.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName TestVo
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/14 10:56
 * @Version 1.0
 **/

public class TestVo {



//    使用hibernate validator出现上面的错误， 需要 注意
//
//    @NotNull 和 @NotEmpty  和@NotBlank 区别
//
//    @NotEmpty 用在集合类上面
//    @NotBlank 用在String上面
//
//    @NotNull    用在基本类型上
//
//    在枚举类上不要加非空注解

    //NotBlank主要判断字符串类型
    @NotNull(message = "msg不能为空")
    private String msg;

    //NOTnull主要判断包装基本数据类类型
    @NotNull(message = "id不能为空")
    @Max(value = 20,message = "id不能大于20")
    @Min(value = 0,message = "id不能小于0")
    private Integer id;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
