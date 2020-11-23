package com.mmall.controller;

import com.mmall.model.SysUser;
import com.mmall.service.SysUserService;
import com.mmall.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.provider.MD5;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService userService;


    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //查询用户
        SysUser sysUser = userService.findByKeyword(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");
        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不能为空!";
        }else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不能为空!";
        }else if (sysUser == null) {
            errorMsg = "该用户系统不存在!";
        }else if (sysUser.getPassword().equals(MD5Utils.encrypt(password))){
            errorMsg = "用户名或密码错误!";
        }else if (sysUser.getStatus() != 1) {
            errorMsg = "该账号已被冻结，请联系管理员!";
        }else {//登录
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)){
                response.sendRedirect(ret);
            }else {
                response.sendRedirect("/admin/index.page");
            }
        }
        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)){
            request.setAttribute("ret", ret);
        }
//        signin.jsp
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request, response);

    }
}
