package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.AclModuleParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/sys/acl")
public class SysAclModuleController {

    /**
     * 权限模块页面
     * @return
     */
    @RequestMapping("acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    /**
     * 添加权限模块
     * @return
     */
    @RequestMapping("/save.json")
    public JsonData saveAclModule(AclModuleParam moduleParam){
        return null;
    }

    /**
     * 修改权限模块
     * @return
     */
    @RequestMapping("/update.json")
    public JsonData updateAclModule(AclModuleParam moduleParam){
        return null;
    }
}
