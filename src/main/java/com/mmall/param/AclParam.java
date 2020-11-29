package com.mmall.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Validation;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @ClassName AclParam
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/29 16:31
 * @Version 1.0
 **/

public class AclParam {

    /**
     * 权限id
     */
    private Integer id;



    /**
     * 权限名称
     */
    @NotBlank(message = "权限点名称不能为空")
    @Length(min = 2,max = 20,message = "权限点名称长度应在2-20个字符之间")
    private String name;

    /**
     * 权限所在的权限模块id
     */
    @NotNull(message = "权限所在的权限模块ID不能为空")
    private Integer aclModuleId;

    /**
     * 请求的url, 可以填正则表达式
     */
    @NotBlank(message = "请求url不能为空")
    @Length(min = 6,max = 20,message = "请求URL不能为空")
    private String url;

    /**
     * 类型，1：菜单，2：按钮，3：其他
     */
    @NotNull(message = "至少要选择一种权限点类型")
    @Max(value = 1,message = "权限点类型非法")
    @Max(value = 3,message = "权限点类型非法")
    private Integer type;

    /**
     * 状态，1：正常，0：冻结
     */
    @NotNull(message = "至少要选择一种权限点状态")
    @Max(value = 1, message = "权限点状态非法")
    @Max(value = 0, message = "权限点状态非法")
    private Integer status;

    /**
     * 权限在当前模块下的顺序，由小到大
     */
    @NotNull(message = "权限点在当前模块下的顺序不能为空")
    private Integer seq;

    /**
     * 备注
     */
    @Length(max = 200, message = "权限点备注最大字符长度200个字符")
    private String remark;

    public AclParam() {
    }

    public AclParam(Integer id, String name, @NotNull(message = "权限所在的权限模块ID不能为空") Integer aclModuleId, String url, @NotNull(message = "至少要选择一种权限点类型") @Max(value = 1, message = "权限点类型非法") @Max(value = 3, message = "权限点类型非法") Integer type, @NotNull(message = "至少要选择一种权限点状态") @Max(value = 1, message = "权限点状态非法") @Max(value = 0, message = "权限点状态非法") Integer status, @NotNull(message = "权限点在当前模块下的顺序不能为空") Integer seq, String remark) {
        this.id = id;
        this.name = name;
        this.aclModuleId = aclModuleId;
        this.url = url;
        this.type = type;
        this.status = status;
        this.seq = seq;
        this.remark = remark;
    }


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

    public Integer getAclModuleId() {
        return aclModuleId;
    }

    public void setAclModuleId(Integer aclModuleId) {
        this.aclModuleId = aclModuleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "AclParam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", aclModuleId=" + aclModuleId +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", seq=" + seq +
                ", remark='" + remark + '\'' +
                '}';
    }
}
