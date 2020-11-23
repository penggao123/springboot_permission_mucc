package com.mmall.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserParam {


    @NotBlank(message = "用户名不能为空")
    @Length(min = 2,max = 50,message = "用户名长度在2-50个字符")
    private String username;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Length(min = 1,max = 15,message = "手机号长度在2-15个字符以内")
    private String telephone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Length(min = 2,max = 20,message = "邮箱字符长度应在2-20个字符")
    private String mail;

    @NotNull(message ="至少要选择一个部门")
    private Integer deptId;

    /**
     * 状态，1：正常，0：冻结状态，2：删除
     */
    @NotNull(message = "必须要指定用户的状态")
    @Min(value = 0,message = "用户状态不合法")
    @Max(value = 2,message = "用户状态不合法")
    private Integer status;


    /**
     * 备注
     */
    @Length(message = "描述信息字符长度不能超过200个字符")
    private String remark;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserParam{" +
                "username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mail='" + mail + '\'' +
                ", deptId=" + deptId +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
