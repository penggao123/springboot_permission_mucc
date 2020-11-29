package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;
import com.mmall.service.SysRoleService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Date;
import java.util.List;

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
}
