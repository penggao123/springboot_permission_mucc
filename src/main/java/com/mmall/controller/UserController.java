package com.mmall.controller;


import com.mmall.common.JsonData;
import com.mmall.param.UserParam;
import com.mmall.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private SysUserService userService;

    /**
     * 添加用户
     */
    public JsonData save(UserParam param){

        int flag = userService.save(param);
        if (flag >= 1) {
            return JsonData.success("添加成功");
        }
        return JsonData.fail("添加失败");
    }


    /**
     * 更新用户
     * @return
     */
    public JsonData update(UserParam param){

        int flag = userService.update(param);
        if (flag >= 1) {
            return JsonData.success("更新成功");
        }
        return JsonData.fail("更新失败");
    }
}
