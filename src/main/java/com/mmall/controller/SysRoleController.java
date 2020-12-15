package com.mmall.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mmall.common.JsonData;
import com.mmall.model.SysRole;
import com.mmall.model.SysRoleUser;
import com.mmall.model.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.service.SysRoleAclService;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysRoleUserService;
import com.mmall.service.SysUserService;
import com.mmall.service.impl.SysTreeService;
import com.mmall.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService roleAclService;

    @Autowired
    private SysRoleUserService roleUserService;

    @Autowired
    private SysUserService userService;


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

    @RequestMapping("/update.json")
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


    @ResponseBody
    @RequestMapping("roleTree.json")
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }


    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        roleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }


    /**
     * 获取已选用户和未选用户
     * @param roleId
     * @return
     */
    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        List<SysUser> checkedUserList = roleUserService.getListByRoleId(roleId);//已选中
        //查询所有用户
        List<SysUser> allUserList = userService.getAll();
        Set<Integer> checkedUserIdSet = Sets.<Integer>newHashSet();
        if (CollectionUtils.isNotEmpty(checkedUserList)) {
            checkedUserIdSet = checkedUserList.stream().map(checkedUser -> checkedUser.getId()).collect(Collectors.toSet());
        }
        //存放未选中的用户集合
        List<SysUser> unselectedUserList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(allUserList)) {
            for (SysUser sysUser: allUserList) {
                if (sysUser.getStatus() == 1 && !checkedUserIdSet.contains(sysUser.getId())) {
                    unselectedUserList.add(sysUser);
                }

            }

        }
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", checkedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }
}
