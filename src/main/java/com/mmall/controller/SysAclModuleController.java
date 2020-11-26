package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/sys/acl")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService aclModuleService;

    /**
     * 权限模块页面
     * @return
     */
    @RequestMapping("acl.page")
    @ResponseBody
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    /**
     * 添加权限模块
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam moduleParam){
        aclModuleService.save(moduleParam);
        return JsonData.success();
    }

    /**
     * 修改权限模块
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam moduleParam){
        aclModuleService.update(moduleParam);
        return JsonData.success();
    }
}
