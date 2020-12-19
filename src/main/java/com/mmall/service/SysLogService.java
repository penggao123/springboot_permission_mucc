package com.mmall.service;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.model.*;
import com.mmall.param.SearchLogParam;

public interface SysLogService {
    void recover(int id);

    PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page);


    void saveDeptLog(SysDept before, SysDept after);

    void saveUserLog(SysUser before, SysUser after);

    void saveAclModuleLog(SysAclModule before, SysAclModule after);

    void saveAclLog(SysAcl before, SysAcl after);

    void saveRoleLog(SysRole before, SysRole after);
}
