package com.mmall.model;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user
 * @author 
 */
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 用户所在部门的id
     */
    private Integer deptId;

    /**
     * 状态，1：正常，0：冻结状态，2：删除
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 最后一次更新时间
     */
    private Date operateTime;

    /**
     * 最后一次更新者的ip地址
     */
    private String operateIp;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysUser other = (SysUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getOperateTime() == null ? other.getOperateTime() == null : this.getOperateTime().equals(other.getOperateTime()))
            && (this.getOperateIp() == null ? other.getOperateIp() == null : this.getOperateIp().equals(other.getOperateIp()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getOperateTime() == null) ? 0 : getOperateTime().hashCode());
        result = prime * result + ((getOperateIp() == null) ? 0 : getOperateIp().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", telephone=").append(telephone);
        sb.append(", mail=").append(mail);
        sb.append(", password=").append(password);
        sb.append(", deptId=").append(deptId);
        sb.append(", status=").append(status);
        sb.append(", remark=").append(remark);
        sb.append(", operator=").append(operator);
        sb.append(", operateTime=").append(operateTime);
        sb.append(", operateIp=").append(operateIp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}