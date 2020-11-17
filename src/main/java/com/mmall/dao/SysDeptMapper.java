package com.mmall.dao;

import com.mmall.model.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getAllDept();

    List<SysDept> getChildDeptListByLevel(@Param("level") String level);


    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);


    int countByNameAndParentId(@Param("parentId") int  parentId, @Param("name") String name, @Param("id") Integer id);

}