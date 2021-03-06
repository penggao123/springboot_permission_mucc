package com.mmall.service;

import com.mmall.model.SysAcl;

import java.util.List;

public interface SysCoreService {

    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getRoleAclList(int roleId);

    List<SysAcl> getUserAclList(int userId);

    boolean hasUrlAcl(String contextPath);
}
