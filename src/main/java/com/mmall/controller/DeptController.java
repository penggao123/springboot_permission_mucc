package com.mmall.controller;

import com.mmall.DeptLevelDto;
import com.mmall.common.JsonData;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.service.impl.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/14 16:18
 * @Version 1.0
 **/

@Controller
@RequestMapping("/sys/dept")
public class DeptController {


    @Autowired
    private SysDeptService deptService;


    @Autowired
    private SysTreeService treeService;


    @RequestMapping("/page.json")
//    @ResponseBody
    public ModelAndView page(){
        return new ModelAndView("dept");
//        return "dept";
    }


    /**
      * @Author gaopeng
      * @Description //添加部门
      * @Date 16:20 2020/11/14
      * @param
      * @return com.mmall.common.JsonData
      **/
    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save(DeptParam param) {

        int saveResult = deptService.save(param);
        if (saveResult == 1){//添加成功
            return JsonData.success();
        }else {//添加失败
            return JsonData.fail("添加失败");
        }
    }


    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> dtoList = treeService.deptTree();
        return JsonData.success(dtoList);
    }


    /**
     * @Author gaopeng
     * @Description //修改部门
     * @Date 16:20 2020/11/14
     * @param
     * @return com.mmall.common.JsonData
     **/
    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData update(DeptParam param) {

        deptService.update(param);
        return JsonData.success();
    }
}
