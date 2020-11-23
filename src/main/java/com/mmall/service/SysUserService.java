package com.mmall.service;

import com.mmall.model.SysUser;
import com.mmall.param.UserParam;

public interface SysUserService {

    int save(UserParam param);

    int update(UserParam param);

    SysUser findByKeyword(String username);
}
