package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.common.JsonData;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.text.normalizer.UBiDiProps;

/**
 * @ClassName SysAclController
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/29 16:44
 * @Version 1.0
 **/

@RequestMapping("/sys/acl")
@Controller
public class SysAclController {

    @Autowired
    private SysAclService aclService;


    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save(AclParam param) {
        aclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(AclParam param){
        aclService.update(param);
        return JsonData.success();
    }


    /**
      * @Author gaopeng
      * @Description //查询权限模块下所有的权限点列表
      * @Date 20:21 2020/11/29
      * @param
      * @return com.mmall.common.JsonData
      **/
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(aclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }
}

























