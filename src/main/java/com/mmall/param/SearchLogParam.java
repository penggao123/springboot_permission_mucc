package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class SearchLogParam {

    private Integer type; // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime;//yyyy-MM-dd HH:mm:ss

    private String toTime;

    public SearchLogParam() {
    }

    public SearchLogParam(Integer type, String beforeSeg, String afterSeg, String operator, String fromTime, String toTime) {
        this.type = type;
        this.beforeSeg = beforeSeg;
        this.afterSeg = afterSeg;
        this.operator = operator;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBeforeSeg() {
        return beforeSeg;
    }

    public void setBeforeSeg(String beforeSeg) {
        this.beforeSeg = beforeSeg;
    }

    public String getAfterSeg() {
        return afterSeg;
    }

    public void setAfterSeg(String afterSeg) {
        this.afterSeg = afterSeg;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
