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
        //1、查询出所有的数据
        List<SysDept> deptList = deptMapper.getAllDept();
        //2、创建一个新的dto集合
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        //3、将查询出的结果拷贝成dto对象，并将拷贝的对象信息存放在dto集合中
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adept(dept);
            dtoList.add(dto);
        }
        //4、将拷贝出来的dto集合调用该方法进行抽取出根节点集合
        return deptListToTree(dtoList);
    }

    /**
     * 从所有的部门信息中，将根部门进行抽取出来，并将根部门按seq字段进行从小到大进行排序
     * @param deptLevelList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) { //接收dto查询的数据
        //1、校验传递的集合是否为空集合
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        //2、创建一个dto集合，用于存放顶级部门对象
        List<DeptLevelDto> rootList = Lists.newArrayList();
        //3、循环遍历方法接收的所有部门信息，将根-部门对象添加到根-部门集合中
        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtils.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序  4、将根部门根据seq字段进行从小到大进行排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // 递归生成树
        transformDeptTree(rootList, LevelUtils.ROOT, levelDeptMap);
        return rootList;
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1
    // level:0.2    参数1：上级部门集合  上级部门的层级  所有的部门信息
    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        //1、遍历所有父部门集合
        for (int i = 0; i < deptLevelList.size(); i++) {
            // 遍历该层的每个元素  2、获取某一个部门信息
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据   //3、通过当前的层级和当前部门的上级部门id  获取当前的level层级
            String nextLevel = LevelUtils.calculateLevel(level, deptLevelDto.getId());
            // 处理下一层     //获取当前level的当前上级id的数据
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
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































