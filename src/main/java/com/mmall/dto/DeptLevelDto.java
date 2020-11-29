package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.model.SysDept;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName DeptLevelDto
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/14 17:10
 * @Version 1.0
 **/

public class DeptLevelDto extends SysDept{

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adept(SysDept dept){
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept,dto );//两个实体类的拷贝
        return dto;
    }

    public List<DeptLevelDto> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptLevelDto> deptList) {
        this.deptList = deptList;
    }

    @Override
    public String toString() {
        return "DeptLevelDto{" +
                "deptList=" + deptList +
                '}';
    }
}
