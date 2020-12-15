package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.model.SysUser;
import com.mmall.service.SysRoleUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
