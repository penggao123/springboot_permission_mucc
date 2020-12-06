package com.mmall.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dto.AclDto;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.dto.DeptLevelDto;
import com.mmall.dao.SysDeptMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclModule;
import com.mmall.model.SysDept;
import com.mmall.service.SysCoreService;
import com.mmall.utils.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SysAclModuleMapper aclModuleMapper;

    @Autowired
    private SysCoreService coreService;

    @Autowired
    private SysAclMapper aclMapper;

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
     *
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


    /**
     * @param
     * @return java.util.List<com.mmall.dto.AclModuleLevelDto>
     * @Author gaopeng
     * @Description //获取权限模块树结构
     * @Date 13:40 2020/11/29
     **/
    public List<AclModuleLevelDto> aclModuleTree() {
        //1、获取苏搜友权限模块数据
        List<SysAclModule> aclModuleList = aclModuleMapper.getAllAclModule();
        //2、定义树形结构集合
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        //3、遍历查询的结果(遍历进行拷贝dto)
        for (SysAclModule aclModule : aclModuleList) {
            AclModuleLevelDto adapt = AclModuleLevelDto.adapt(aclModule);
            dtoList.add(adapt);
        }
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        //1、判断接收的参数是否为空
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        //2、创建集合 用于存放根节点数据
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        //3、循环遍历所有数据，将所有数据存到MultiMap集合中，key为level，value存放相同level的集合，
        //4、循环遍历同时判断如果是根节点中的数据存放到root节点集合中
        for (AclModuleLevelDto aclModule : dtoList) {
            levelAclModuleMap.put(aclModule.getLevel(), aclModule);
            if (LevelUtils.ROOT.equals(aclModule.getLevel())) {
                rootList.add(aclModule);
            }
        }
        Collections.sort(rootList, aclModuSeqComparator);
        treansformAclModuleTree(rootList, LevelUtils.ROOT, levelAclModuleMap);

        return rootList;

    }

    /**
     * @Author gaopeng
     * @Description //权限模块的比较树（从小到大进行排序）
     * @Date 14:04 2020/11/29
     * @param
     * @return
     **/
    public Comparator<AclModuleLevelDto> aclModuSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    /**
     * @param
     * @return void
     * @Author gaopeng
     * @Description //组合权限模块的树数据
     * @Date 14:10 2020/11/29
     **/
    public void treansformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            //1、获取当前层级的下一层级的level
            String nestLevel = LevelUtils.calculateLevel(dto.getLevel(), dto.getId());
            //2、从map集合中获取相同level的数据集合
            List<AclModuleLevelDto> aclModuleList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nestLevel);
            //3、判断是否有对应的权限模块
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                //进行排序
                Collections.sort(aclModuleList, aclModuSeqComparator);
                dto.setAclModuleDtoList(aclModuleList);
                treansformAclModuleTree(aclModuleList, dto.getLevel(), levelAclModuleMap);
            }
        }
    }


    /**
      * @Author gaopeng
      * @Description //角色模块的数据树结构
      * @Date 14:28 2020/12/5
      * @param
      * @return
      **/
    public List<AclModuleLevelDto> roleTree(int roleId){
        // 1、当前用户已分配的权限点
        List<SysAcl> userAclList = coreService.getCurrentUserAclList();
        // 2、当前角色分配的权限点
        List<SysAcl> roleAclList = coreService.getRoleAclList(roleId);
        // 3、当前系统所有权限点
        List<AclDto> aclDtoList = Lists.newArrayList();

        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        List<SysAcl> allAclList = aclMapper.getAll();
        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);

    }

    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for(AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>)moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, aclSeqComparator);
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleDtoList(), moduleIdAclMap);
        }
    }

    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}































