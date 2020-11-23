package com.mmall.utils;


import com.sun.org.apache.regexp.internal.RE;

import java.util.Date;
import java.util.Random;

/**
 * 生成随机密码
 */
public class PasswordUtils {

    private final static String[] num = {
            "a","b","c","d","e","f","g","h","j","k","m","n",
            "o","p","q","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","J","K","M","N",
            "O","P","Q","U","N","W","X","Y","Z",
    };

    private final static  String[] word = {
            "2","3","4","5","6","7","8","9"
    };

    public static String randomPassWord(){
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(new Date().getTime());
        boolean flag = false;
        //生成随机长度，定为密码长度
        int length = random.nextInt(3) + 8;
        for (int i = 0;i < length; i++) {
            if (flag) {
                stringBuffer.append(num[random.nextInt(num.length)]);
            }else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }

}
