package com.mmall.controller;


import com.mmall.common.JsonData;
import com.mmall.param.UserParam;
import com.mmall.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Autowired
    private SysUserService userService;

    /**
     * 添加用户
     */
    @RequestMapping("/save.json")
    @ResponseBody
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
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(UserParam param){

        int flag = userService.update(param);
        if (flag >= 1) {
            return JsonData.success("更新成功");
        }
        return JsonData.fail("更新失败");
    }
}
