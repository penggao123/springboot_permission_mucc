package com.mmall.utils;

import com.google.common.base.Splitter;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    public static List<Integer> splitToListInt(String str) {
        List<String> stringList = (List<String>) Splitter.on(',').trimResults().omitEmptyStrings().split(str);
        return stringList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }



}
