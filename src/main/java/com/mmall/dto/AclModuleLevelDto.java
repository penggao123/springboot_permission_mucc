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


    public List<AclModuleLevelDto> getAclModuleDtoList() {
        return aclModuleDtoList;
    }

    public void setAclModuleDtoList(List<AclModuleLevelDto> aclModuleDtoList) {
        this.aclModuleDtoList = aclModuleDtoList;
    }

    @Override
    public String toString() {
        return "AclModuleLevelDto{" +
                "aclModuleDtoList=" + aclModuleDtoList +
                '}';
    }
}
