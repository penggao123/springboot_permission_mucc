package com.mmall.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysUser;
import com.mmall.service.SysCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SysCoreServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/12/6 18:11
 * @Version 1.0
 **/

@Service
@Transactional
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private SysAclMapper aclMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysRoleAclMapper roleAclMapper;




    /**
     * @Author gaopeng
     * @Description //获取用户的权限点列表
     * @Date 18:11 2020/12/6
     * @param
     * @return java.util.List<com.mmall.model.SysAcl>
     **/
    @Override

    public List<SysAcl> getCurrentUserAclList() {
        //从request中获取用户信息
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    /**
     * @param
     * @return java.util.List<com.mmall.model.SysAcl>
     * @Author gaopeng
     * @Description //获取角色的权限点列表
     * @Date 18:12 2020/12/6
     **/
    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = roleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        System.out.println(aclIdList);
        if (CollectionUtils.isEmpty(aclIdList)) {
            List<SysAcl> list = new ArrayList<>();
            return list;
        }
        return aclMapper.getByIdList(aclIdList);
    }


    /**
     * @param
     * @return java.util.List<com.mmall.model.SysAcl>
     * @Author gaopeng
     * @Description //获取某个用户的权限点列表
     * @Date 18:14 2020/12/6
     **/
    public List<SysAcl> getUserAclList(int userId) {
        //校验是否为超级用户
        if (isSuperAdmin()) {
            //获取所有的权限点
            List<SysAcl> aclList = aclMapper.getAll();
            return aclList;
        }
        //获取该用户的所有角色列表
        List<Integer> userRoleIdList = roleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        //角色id列表查询所有的权限点数据
        List<Integer> userAclIdList = roleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return aclMapper.getByIdList(userAclIdList);
    }

    /**
     * @param
     * @return boolean
     * @Author gaopeng
     * @Description //校验是否是超级用户
     * @Date 18:15 2020/12/6
     **/
    public boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }


    @Override
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
        List<SysAcl> aclList = aclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }

//        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
//        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            if (acl == null || acl.getStatus() != 1) { // 权限点无效
                continue;
            }
            hasValidAcl = true;
//            if (userAclIdSet.contains(acl.getId())) {
//                return true;
//            }
        }
        if (!hasValidAcl) {
            return true;
        }
        return false;
    }
}
