package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface SysAclMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByNameAndAclModuleId(@Param("aclModuleId") Integer aclModuleId,@Param("name")  String name,@Param("id")  Integer id);

    int countByAclModuleId(@Param("aclModuleId") Integer aclModuleId,@Param("page")  PageQuery page);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") Integer aclModuleId,@Param("page") PageQuery page);

    List<SysAcl> getAll();

    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

    int getAclCountByAclModule(int aclModuleId);
}