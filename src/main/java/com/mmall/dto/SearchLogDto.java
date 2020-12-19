package com.mmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

public class SearchLogDto {

    private Integer type; // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private Date fromTime;//yyyy-MM-dd HH:mm:ss

    private Date toTime; //yyyy-MM-dd HH:mm:ss

    public SearchLogDto() {
    }

    public SearchLogDto(Integer type, String beforeSeg, String afterSeg, String operator, Date fromTime, Date toTime) {
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

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return "SearchLogDto{" +
                "type=" + type +
                ", beforeSeg='" + beforeSeg + '\'' +
                ", afterSeg='" + afterSeg + '\'' +
                ", operator='" + operator + '\'' +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                '}';
    }
}
