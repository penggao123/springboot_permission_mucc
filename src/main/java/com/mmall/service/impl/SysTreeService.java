package com.mmall.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.DeptLevelDto;
import com.mmall.dao.SysDeptMapper;
import com.mmall.model.SysDept;
import com.mmall.utils.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName SysTreeService
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/16 20:54
 * @Version 1.0
 **/

@Service
@Transactional
public class SysTreeService {

    @Autowired
    private SysDeptMapper deptMapper;

    public List<DeptLevelDto> deptTree() {

        List<SysDept> deptList = deptMapper.getAllDept();//查询所有部门数据

        ArrayList<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adept(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {

        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }

        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);

            if (LevelUtils.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        //按照从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        transformDeptTree(deptLevelList,LevelUtils.ROOT, levelDeptMap );
        return rootList;
    }


    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {

        for (int i= 0;i < deptLevelList.size(); i++){
            //遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            //处理当前层的数据
            String nextLevel = LevelUtils.calculateLevel(level, deptLevelDto.getParentId());
            //处理下一层

            List<DeptLevelDto> tempDeptList  = (List<DeptLevelDto>)levelDeptMap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tempDeptList)){

                //排序
                Collections.sort(tempDeptList,deptSeqComparator);

                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入下一层处理
                transformDeptTree(tempDeptList,nextLevel , levelDeptMap);

            }
        }
    }



    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}































