package com.mmall.service;

import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;

import java.util.List;

public interface SysRoleService {


    void save(RoleParam param);

    void update(RoleParam param);


    List<SysRole> getAll();

    List<SysRole> getRoleListByUserId(int userId);
}
