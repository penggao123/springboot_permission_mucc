package com.mmall.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName DeptParam
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/14 15:55
 * @Version 1.0
 **/

public class DeptParam {


    private Integer id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15, min = 2, message = "部门名称长度需要在2-15个字之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150, message = "备注的长度需要在150个字以内")
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
