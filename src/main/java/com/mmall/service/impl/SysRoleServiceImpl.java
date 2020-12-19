package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysRole;
import com.mmall.model.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.service.SysLogService;
import com.mmall.service.SysRoleService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 21:49
 * @Version 1.0
 **/

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;


    @Autowired
    private SysRoleAclMapper roleAclMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysLogService sysLogService;



    @Override
    public void save(RoleParam param) {
        BeanValidator.check(param);
        boolean checkExits = checkExits(param.getName(), param.getId());
        if (checkExits) {
            throw new ParamException("相同名称的角色系统中已存在");
        }
        SysRole before = new SysRole();
        before.setName(param.getName());
        before.setRemark(param.getRemark());
        before.setStatus(param.getStatus());
        before.setType(param.getType());
        before.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        before.setOperateTime(new Date());
        before.setOperator(RequestHolder.getCurrentUser().getUsername());
        roleMapper.insertSelective(before);
        sysLogService.saveRoleLog(null, before);

    }

    @Override
    public void update(RoleParam param) {
        BeanValidator.check(param);
        boolean checkExits = checkExits(param.getName(), param.getId());
        if (checkExits) {
            throw new ParamException("相同名称的角色系统中已存在");
        }
        SysRole after = roleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(after, "待更新的角色系统中不存在");
        SysRole role = new SysRole();
        role.setId(param.getId());
        role.setType(param.getType());
        role.setStatus(param.getStatus());
        role.setRemark(param.getRemark());
        role.setName(param.getName());
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateTime(new Date());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        roleMapper.updateByPrimaryKeySelective(role);
        sysLogService.saveRoleLog(after, role);
    }

    @Override
    public List<SysRole> getAll() {
        return roleMapper.getAll();
    }

    /**
      * @Author gaopeng
      * @Description //系统中是否已经存在相同名称的角色
      * @Date 21:08 2020/11/29
      * @param
      * @return boolean
      **/
    public boolean checkExits(String name, Integer id) {
        return roleMapper.countByName(name, id) > 0;
    }


    /**
     * 获取用户的所有角色数据
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getRoleListByUserId(int userId) {
        //获取角色id
        List<Integer> roleIdList = roleUserMapper.getRoleIdListByUserId(userId);
        //查询角色信息
        List<SysRole> roleList = roleMapper.getRoleListByIds(roleIdList);

        return roleList;
    }


    /**
     * 权限点查询对应的角色数据
     * @param aclId
     * @return
     */
    @Override
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIdList = roleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.<SysRole>newArrayList();
        }
        //角色id查询对应的角色对象
        List<SysRole> roleList = roleMapper.getRoleListByIds(roleIdList);
        return roleList;
    }

    /**
     * 查询对应的
     * @param roleList
     * @return
     */
    @Override
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());//角色id集合
        //查询角色对应的用户id
        List<Integer> userIdList = roleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.<SysUser>newArrayList();
        }
        //用户id查询用户数据
        List<SysUser> userList = userMapper.getListByUserIds(userIdList);

        return userList;
    }
}
