package com.mmall.common;

import com.mmall.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于获取当前登录用户信息
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();


    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }


    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    /**
     * 获取用户信息
     */
    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }


}
