package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.model.SysAclModule;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName AclModuleLevelDto
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/29 13:29
 * @Version 1.0
 **/

public class AclModuleLevelDto extends SysAclModule{

    private List<AclModuleLevelDto> aclModuleDtoList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }

    /**
      * @Author gaopeng
      * @Description //每个权限点模块下有多个权限点
      * @Date 14:30 2020/12/5
      * @param
      * @return
      **/
    private List<AclDto>  aclList = Lists.newArrayList();


    public List<AclModuleLevelDto> getAclModuleDtoList() {
        return aclModuleDtoList;
    }

    public void setAclModuleDtoList(List<AclModuleLevelDto> aclModuleDtoList) {
        this.aclModuleDtoList = aclModuleDtoList;
    }


    public List<AclDto> getAclList() {
        return aclList;
    }

    public void setAclList(List<AclDto> aclList) {
        this.aclList = aclList;
    }

    @Override
    public String toString() {
        return "AclModuleLevelDto{" +
                "aclModuleDtoList=" + aclModuleDtoList +
                '}';
    }
}
