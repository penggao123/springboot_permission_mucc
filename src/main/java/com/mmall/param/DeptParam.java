package com.mmall.param;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName DeptParam
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/14 15:55
 * @Version 1.0
 **/

public class DeptParam {


    /**
     * 部门id
     */
    private Integer id;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Length(max = 20, min = 2,message = "名称字符长度应在2-20之间")
    private String name;


    /**
     * 上级部门id
     */
    private Integer parentId;


    /**
     * 部门在当前层级下的顺序，由小到大
     */
    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    /**
     * 备注
     */
    @Length(max = 150, message = "备注的长度最大为150个字符")
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DeptParam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", seq=" + seq +
                ", remark='" + remark + '\'' +
                '}';
    }
}
