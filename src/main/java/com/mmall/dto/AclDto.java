package com.mmall.dto;

import com.mmall.model.SysAcl;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName AclDto
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/12/5 14:31
 * @Version 1.0
 **/

public class AclDto extends SysAcl{


    //是否默认选中
    private boolean checked = false;

    //是否可以操作这个权限
    private boolean hasAcl = false;


    //拷贝对象
    public static AclDto adapt(SysAcl acl){
        AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(acl, aclDto);
        return aclDto;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHasAcl() {
        return hasAcl;
    }

    public void setHasAcl(boolean hasAcl) {
        this.hasAcl = hasAcl;
    }



}
