package com.mmall.service;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;

public interface SysAclService {
    void save(AclParam param);

    void update(AclParam param);

    PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery);
}
