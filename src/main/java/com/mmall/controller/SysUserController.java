package com.mmall.controller;


import com.google.common.collect.Maps;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysUserService;
import com.mmall.service.impl.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleService sysRoleService;

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

    /**
     * 获取部门信息
     * @return
     */
    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery){

        PageResult<SysUser> list = userService.getPageByDeptId(deptId, pageQuery);

        return JsonData.success(list);
    }

    /**
     * 获取用户的权限点树数据
     * 获取用户的角色
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));//获取权限点树结构
        map.put("roles", sysRoleService.getRoleListByUserId(userId));//获取角色
        return JsonData.success(map);
    }
}
