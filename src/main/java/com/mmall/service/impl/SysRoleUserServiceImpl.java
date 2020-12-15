package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.model.SysRoleUser;
import com.mmall.model.SysUser;
import com.mmall.service.SysRoleUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SysRoleUserServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 21:50
 * @Version 1.0
 **/

@Transactional
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询已选中的用户
     * @param roleId
     * @return
     */
    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = roleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        //用户id集合查询对应的用户
        List<SysUser> userList = userMapper.getListByUserIds(userIdList);
        return userList;
    }


    /**
     * 角色与用户关系维护
     * @param roleId
     * @param userIdList
     */
    @Override
    public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        //角色id查询所有数据
        List<Integer> originUserIdList = roleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdList.removeAll(userIdList);
            if (CollectionUtils.isEmpty(originUserIdList)) {
                return;
            }
        }

        updateRoleUser(roleId, userIdList);

    }


    /**
     * 更新用户与角色数据
     * @param roleId
     * @param userIdList
     */
    private void updateRoleUser(int roleId, List<Integer> userIdList){
        //删除所有数据
        roleUserMapper.deleteByRoleId(roleId);

        //添加数据
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId: userIdList) {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setOperateIp(RequestHolder.getCurrentUser().getOperateIp());
            roleUser.setOperateTime(new Date());
            roleUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);
            roleUserList.add(roleUser);
        }
        if (CollectionUtils.isEmpty(roleUserList)) {
            return;
        }
        //插入数据库
        roleUserMapper.batchInsertRoleUser(roleUserList);

    }
}

























