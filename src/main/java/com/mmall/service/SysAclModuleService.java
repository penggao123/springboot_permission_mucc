package com.mmall.service;

import com.mmall.dto.AclModuleLevelDto;
import com.mmall.param.AclModuleParam;

import java.util.List;

public interface SysAclModuleService {


    int save(AclModuleParam moduleParam);

    int update(AclModuleParam moduleParam);


    void delete(int id);
}
