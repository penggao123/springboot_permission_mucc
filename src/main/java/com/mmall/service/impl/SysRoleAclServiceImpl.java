package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.beans.LogType;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysLogMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.model.SysLogWithBLOBs;
import com.mmall.model.SysRoleAcl;
import com.mmall.service.SysRoleAclService;
import com.mmall.utils.IpUtil;
import com.mmall.utils.JsonMapper;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @ClassName SysRoleAclServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 21:48
 * @Version 1.0
 **/

@Transactional
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {


    @Autowired
    private SysRoleAclMapper roleAclMapper;

    @Autowired
    private SysLogMapper sysLogMapper;


    @Override
    public void changeRoleAcls(int roleId, List<Integer> aclIdList) {

        //1、角色id查询所有角色权限数据
        List<Integer> originAclIdList =  roleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));

        //2、比较接受参数权限和数据库权限数据是否一致
        if (originAclIdList.size() == aclIdList.size()) {
            HashSet<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            HashSet<Integer> aclIdListSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdListSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        //3、更新权限
        updateRoleAcls(roleId, aclIdList);
        saveRoleAclLog(roleId, originAclIdList, aclIdList);

    }

    /**
     * 更新角色数据信息
     * @param roleId
     * @param aclIdList
     */
    public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        //删除该角色的所有角色权限点
        roleAclMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }

        //创建新集合用于存放新创建的roleAcl
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl roleAcl = new SysRoleAcl();
            roleAcl.setAclId(aclId);
            roleAcl.setOperateIp(RequestHolder.getCurrentUser().getOperateIp());//操作ip地址
            roleAcl.setOperateTime(new Date());
            roleAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleAcl.setRoleId(roleId);
            roleAclList.add(roleAcl);
        }
        if (CollectionUtils.isEmpty(roleAclList)) {
            return;
        }
        roleAclMapper.batchInsert(roleAclList);

    }

    private void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }
}
