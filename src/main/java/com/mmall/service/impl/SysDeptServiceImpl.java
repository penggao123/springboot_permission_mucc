package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        dept.setOperator("system");
        dept.setOperateIp("127.0.0.1");
        dept.setOperateTime(new Date());
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
        after.setOperator("system-update");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());
        updateWithChild(before, after);
    }


    //当前level更新后需要更新子部门
    public void updateWithChild(SysDept before, SysDept after) {

        //获取之前的level
        String newLevelPrefix = after.getLevel();
        //获取更新后的level
        String oldLevelPrefix = before.getLevel();

        //校验level是否被更新，如果有更新则需更新子部门信息
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysDept> deptList = deptMapper.getChildDeptListByLevel(oldLevelPrefix);
            //判断子部门是否有数据
            if (CollectionUtils.isNotEmpty(deptList)) {//不为空(有数据)
                for (SysDept dept : deptList) {
                    //获取当前level值
                    String level = dept.getLevel();

                    if (level.indexOf(oldLevelPrefix) == 0) {//判断当前level是否是父部门下的子部门
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                deptMapper.batchUpdateLevel(deptList);
            }

        }
        deptMapper.updateByPrimaryKey(after);

    }
}
