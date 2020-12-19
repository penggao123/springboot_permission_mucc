package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.service.SysLogService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.IpUtil;
import com.mmall.utils.LevelUtils;
import com.mmall.utils.MD5Utils;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysDeptServiceImpl
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 21:47
 * @Version 1.0
 **/

@Transactional
@Service
public class SysDeptServiceImpl implements SysDeptService {


    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Resource
    private SysLogService sysLogService;


    /**
      * @Author gaopeng
      * @Description //添加部门
      * @Date 16:22 2020/11/14
      * @param
      * @return int
      **/
    @Override
    public int save(DeptParam param) {

        //验证表单是否正确
        BeanValidator.check(param);
        if (this.checkExist(param.getParentId(), param.getName(),param.getId() )){//校验同一层下面是否存在相同的部门
            throw new ParamException("同一层下存在相同部门名称");
        }
        SysDept dept = new SysDept();
        dept.setName(param.getName());
        dept.setParentId(param.getParentId());
        dept.setSeq(param.getSeq());
        dept.setRemark(param.getRemark());
        //上一级的level
        String parentLevel = this.getParentLevel(param.getParentId());//上一级id
        dept.setLevel(LevelUtils.calculateLevel(parentLevel,param.getParentId() ));
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());
        sysLogService.saveDeptLog(null, dept);
        return deptMapper.insertSelective(dept);

    }


    //校验部门是否已存在
    private boolean checkExist(Integer parentId, String deptName, Integer depetId) {
        return deptMapper.countByNameAndParentId(parentId,deptName ,depetId ) > 0;
    }

    //根据上级id查询上级的level
    private String getParentLevel(Integer parentId){
        SysDept dept = deptMapper.selectByPrimaryKey(parentId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }


    @Override
    public void update(DeptParam param) {
        //验证表单是否正确
        BeanValidator.check(param);
        if (this.checkExist(param.getParentId(), param.getName(),param.getId() )){//校验同一层下面是否存在相同的部门
            throw new ParamException("同一层下存在相同部门名称");
        }

        //判断原数据是否存在
        SysDept before = deptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");//校验是否存在并抛出

        SysDept after = new SysDept();
        after.setName(param.getName());
        after.setParentId(param.getParentId());
        after.setSeq(param.getSeq());
        after.setRemark(param.getRemark());
        after.setRemark(param.getRemark());
        after.setId(param.getId());
        after.setLevel(LevelUtils.calculateLevel(getParentLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before, after);
        sysLogService.saveDeptLog(null, after);
    }


    //当前level更新后需要更新子部门(before:修改前的对象 after：修改后的对象)
    public void updateWithChild(SysDept before, SysDept after) {

        //获取更新后的level
        String newLevelPrefix = after.getLevel();
        //获取之前的level
        String oldLevelPrefix = before.getLevel();

        //校验level是否被更新，如果有更新则需更新子部门信息
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysDept> deptList = deptMapper.getChildDeptListByLevel(oldLevelPrefix);
            //判断子部门是否有数据
            if (CollectionUtils.isNotEmpty(deptList)) {//不为空(有数据)
                for (SysDept dept : deptList) {
                    //获取当前level值
                    String level = dept.getLevel();

                    if (level.indexOf(oldLevelPrefix) == 0) {//两个level相等
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                deptMapper.batchUpdateLevel(deptList);
            }

        }
        deptMapper.updateByPrimaryKey(after);

    }


    /**
     * 删除部门
     * @param deptId
     */
    @Override
    public void delete(int deptId) {
        SysDept dept = deptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, "待删除的部门不存在");//校验原数据是否存在
        //2、校验是否有子部门
        int sonDeptCount = deptMapper.getSonDeptCountById(deptId);
        //校验是部门下是否存在用户
        int deptUserCount = userMapper.getUserCountById(deptId);
        if (sonDeptCount > 0) {
            throw new ParamException("该部门下存在子部门，无法进行删除操作");
        }
        if (deptUserCount > 0) {
            throw new ParamException("该部门下存在用户，无法进行删除操作");
        }

        //删除部门信息
        deptMapper.deleteByPrimaryKey(deptId);
    }
}
