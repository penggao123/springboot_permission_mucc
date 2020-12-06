package com.mmall.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.model.SysAcl;
import com.mmall.service.SysCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
      * @Author gaopeng
      * @Description //获取角色的权限点列表
      * @Date 18:12 2020/12/6
      * @param
      * @return java.util.List<com.mmall.model.SysAcl>
      **/
    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = roleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));

        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return aclMapper.getByIdList(aclIdList);
    }


    /**
      * @Author gaopeng
      * @Description //获取某个用户的权限点列表
      * @Date 18:14 2020/12/6
      * @param
      * @return java.util.List<com.mmall.model.SysAcl>
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
      * @Author gaopeng
      * @Description //校验是否是超级用户
      * @Date 18:15 2020/12/6
      * @param
      * @return boolean
      **/
    public boolean isSuperAdmin() {
        return true;
    }
}
