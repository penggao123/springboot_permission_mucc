package com.mmall.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AclModuleParam {

    /**
     * 权限模块id
     */
    private Integer id;

    /**
     * 权限模块名称
     */
    @NotBlank(message = "权限模块名称不能为空")
    @Length(max = 20,min = 2,message = "权限模块名称字符长度只能在2-20个字符")
    private String name;

    /**
     * 上级权限模块id
     */
    private Integer parentId = 0;


    /**
     * 权限模块在当前层级下的顺序，由小到大
     */
    @NotNull(message = "限模块在当前层级下顺序不能为空")
    private Integer seq;

    /**
     * 状态，1：正常，0：冻结
     */
    @NotNull(message = "必须要指定权限模块的状态")
    @Min(value = 0, message = "权限模块状态非法")
    @Max(value = 1, message = "权限模块状态非法")
    private Integer status;

    /**
     * 备注
     */
    @Length(max = 200, message = "备注长度不能超过200个字符")
    private String remark;


}
