package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;
import com.mmall.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @ClassName SysRoleController
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/29 20:58
 * @Version 1.0
 **/

@RequestMapping("/sys/role")
@Controller
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;


    @RequestMapping("/role.page")
    public ModelAndView role(RoleParam param) {

        return new ModelAndView("role");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(RoleParam param) {
        roleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/upate.json")
    @ResponseBody
    public JsonData update(RoleParam param) {
        roleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        List<SysRole> listAll = roleService.getAll();
        return JsonData.success(listAll);
    }
}
