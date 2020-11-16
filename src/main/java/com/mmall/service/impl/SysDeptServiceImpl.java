package com.mmall.service.impl;

import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.utils.BeanValidator;
import com.mmall.utils.LevelUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        return true;
    }

    //根据上级id查询上级的level
    private String getParentLevel(Integer parentId){
        SysDept dept = deptMapper.selectByPrimaryKey(parentId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
