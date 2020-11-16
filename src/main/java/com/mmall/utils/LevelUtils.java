package com.mmall.utils;

import com.mmall.model.SysDept;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName LevelUtils
 * @Description TODO 用于计算level等级
 * @Author gaopeng
 * @Date 2020/11/14 16:31
 * @Version 1.0
 **/

public class LevelUtils {

    //定义分隔符
    public final static String SEPARATOR = ".";

    //定义第一级
    public final static String ROOT = "0";


    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }



    }
}
