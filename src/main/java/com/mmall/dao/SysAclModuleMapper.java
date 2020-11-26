package com.mmall.dao;

import com.mmall.model.SysAclModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameAndParentId(@Param("parentId") Integer parentId,@Param("aclModuleName") String aclModuleName,@Param("aclModuleId") Integer aclModuleId);

    List<SysAclModule> getChildAclModuleByLevel(@Param("afterLevel") String afterLevel);

    void batchUpdateLevel(@Param("beforeList") List<SysAclModule> beforeList);
}