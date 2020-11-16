package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    /**
      * @Author gaopeng
      * @Description //添加部门
      * @Date 16:20 2020/11/14
      * @param
      * @return com.mmall.common.JsonData
      **/
    public JsonData save(DeptParam param) {

        int saveResult = deptService.save(param);
        if (saveResult == 1){//添加成功
            return JsonData.success();
        }else {//添加失败
            return JsonData.fail("添加失败");
        }
    }
}
