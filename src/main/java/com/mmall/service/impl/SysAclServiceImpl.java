package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class SysAclServiceImpl implements SysAclService{

    @Autowired
    SysAclMapper aclMapper;
    @Override
    public void save(AclParam param) {
        //1、校验参数
        BeanValidator.check(param);
        boolean checkExits = checkExits(param.getAclModuleId(), param.getName(), param.getId());
        if (checkExits) {
            throw new ParamException("同一层级下有重复权限点名称");
        }
        SysAcl sysAcl = new SysAcl();
        sysAcl.setAclModuleId(param.getAclModuleId());
        sysAcl.setName(param.getName());
        sysAcl.setRemark(param.getRemark());
        sysAcl.setSeq(param.getSeq());
        sysAcl.setStatus(param.getStatus());
        sysAcl.setType(param.getType());
        sysAcl.setUrl(param.getUrl());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());
        sysAcl.setCode(generrateCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclMapper.insertSelective(sysAcl);
    }

    @Override
    public void update(AclParam param) {
        BeanValidator.check(param);
        boolean checkExits = checkExits(param.getAclModuleId(), param.getName(), param.getId());
        if (checkExits) {
            throw new ParamException("同一层级下有重复权限点名称");
        }
        //查询原数据
        SysAcl before = aclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl newSysAcl = new SysAcl();
        newSysAcl.setId(param.getId());
        newSysAcl.setName(param.getName());
        newSysAcl.setUrl(param.getUrl());
        newSysAcl.setType(param.getType());
        newSysAcl.setStatus(param.getStatus());
        newSysAcl.setSeq(param.getSeq());
        newSysAcl.setRemark(param.getRemark());
        newSysAcl.setAclModuleId(param.getAclModuleId());
        newSysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        newSysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        newSysAcl.setOperateTime(new Date());
        aclMapper.updateByPrimaryKeySelective(newSysAcl);
    }

    /**
      * @Author gaopeng
      * @Description //校验同一层级下是否有重复名称
      * @Date 19:10 2020/11/29
      * @param
      * @return boolean
      **/
    public boolean checkExits(Integer aclModuleId, String name, Integer id) {
        return aclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    /**
      * @Author gaopeng
      * @Description //生成权限编码
      * @Date 19:32 2020/11/29
      * @param
      * @return java.lang.String
      **/
    private String generrateCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date() + "_" + (int)(Math.random() * 100));
    }

    /**
      * @Author gaopeng
      * @Description //查询某个权限模块下所有的权限点列表
      * @Date 20:24 2020/11/29
      * @param
      * @return com.mmall.beans.PageResult<com.mmall.model.SysAcl>
      **/
    @Override
    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = aclMapper.countByAclModuleId(aclModuleId, pageQuery);
        if (count > 0) {
            List<SysAcl> aclList = aclMapper.getPageByAclModuleId(aclModuleId, pageQuery);

            PageResult<SysAcl> pageResult = new PageResult<>();
            pageResult.setTotal(count);
            pageResult.setData(aclList);
            return pageResult;
        }
        return new PageResult<>();
    }
}
