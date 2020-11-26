package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.IpUtil;
import com.mmall.utils.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysAclModuleServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 21:44
 * @Version 1.0
 **/

@Transactional
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Autowired
    private SysAclModuleMapper aclModuleMapper;

    /**
     * 权限模块添加
     * @param moduleParam
     * @return
     */
    @Override
    public int save(AclModuleParam moduleParam) {

        BeanValidator.check(moduleParam);
        //校验相同权限模块下是否有相同名称的权限模块
        boolean checkExits = checkExits(moduleParam.getParentId(), moduleParam.getName(), moduleParam.getId());
        if (checkExits) {//存在
            throw new ParamException("同一权限模块下存在相同权限模块名称");
        }
        SysAclModule aclModule = new SysAclModule();
        aclModule.setName(moduleParam.getName());
        aclModule.setParentId(moduleParam.getParentId());
        aclModule.setRemark(moduleParam.getRemark());
        aclModule.setSeq(moduleParam.getSeq());
        aclModule.setStatus(moduleParam.getStatus());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        String parentLevel = this.getParentLevel(moduleParam.getParentId());//根据上级权限模块id获取上级level
        String level = LevelUtils.calculateLevel(parentLevel, moduleParam.getParentId());//根据上级level和上级id拼装成当前权限模块level
        aclModule.setLevel(level);
        return aclModuleMapper.insertSelective(aclModule);
    }

    /**
     * 权限模块修改
     * @param moduleParam
     * @return
     */
    @Override
    public int update(AclModuleParam moduleParam) {
        BeanValidator.check(moduleParam);
        boolean checkExits = this.checkExits(moduleParam.getParentId(), moduleParam.getName(), moduleParam.getId());
        if (checkExits) {
            throw new ParamException("同一权限模块下存在相同权限模块名称");
        }
        //查询原数据
        SysAclModule before = aclModuleMapper.selectByPrimaryKey(moduleParam.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclModule after = new SysAclModule();
        after.setId(moduleParam.getId());
        after.setStatus(moduleParam.getStatus());
        after.setSeq(moduleParam.getSeq());
        after.setRemark(moduleParam.getRemark());
        after.setParentId(moduleParam.getParentId());
        after.setName(moduleParam.getName());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        return updateWithChild(before, after);
    }

    /**
     * 查询统一权限模块下是否有相同的名称是否存在
     * @param parentId  上级id
     * @param aclModuleName 模块名称
     * @param aclModuleId 当前模块id
     * @return
     */
    private boolean checkExits(Integer parentId, String aclModuleName, Integer aclModuleId) {
        return aclModuleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }

    /**
     * 根据上级parentId获取当前权限模块level值
     * @param parentId
     * @return
     */
    private String getParentLevel(Integer parentId) {
        SysAclModule aclModule = aclModuleMapper.selectByPrimaryKey(parentId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }

    /**
     *
     */
    private int updateWithChild(SysAclModule before, SysAclModule after) {

        //获取更新之前的level
        String beforeLevel = before.getLevel();
        //获取更新之后的level
        String afterLevel = after.getLevel();

        //校验level是否修改
        if (!before.equals(after)) {
            //根据未修改前的level查询对应的数据
            List<SysAclModule> beforeList = aclModuleMapper.getChildAclModuleByLevel(afterLevel);
            if (CollectionUtils.isNotEmpty(beforeList)) { //有值情况
                for (SysAclModule aclModule: beforeList) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(beforeLevel) == 0) {
                        level = afterLevel + level.substring(beforeLevel.length());
                        after.setLevel(level);
                    }
                }
                aclModuleMapper.batchUpdateLevel(beforeList);
            }

        }
        return aclModuleMapper.updateByPrimaryKeySelective(after);

    }
}
