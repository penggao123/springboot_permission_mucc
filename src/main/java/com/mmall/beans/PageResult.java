package com.mmall.beans;

import com.google.common.collect.Lists;

import java.util.List;

public class PageResult<T> {

    private List<T> data = Lists.newArrayList();


    private int total = 0;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "data=" + data +
                ", total=" + total +
                '}';
    }
}
