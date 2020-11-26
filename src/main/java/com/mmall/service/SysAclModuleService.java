package com.mmall.service;

import com.mmall.param.AclModuleParam;

public interface SysAclModuleService {


    int save(AclModuleParam moduleParam);

    int update(AclModuleParam moduleParam);
}
