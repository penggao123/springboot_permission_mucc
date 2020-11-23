package com.mmall.dao;

import com.mmall.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(String keyword);

    int countByMail(@Param("mail") String mail, @Param("userId") Integer userId);

    int countByTelephone(@Param("telePhone") String telePhone, @Param("userId") Integer userId);
}