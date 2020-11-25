package com.mmall.beans;

import javax.validation.constraints.Min;

/**
 * 分页查询参数
 */
public class PageQuery {

    @Min(value = 1,message = "当前页面不合法")
    private int  pageNo = 1;


    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize;

    private int offset;

    public int getOffset(){
        return (pageNo - 1) * pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
