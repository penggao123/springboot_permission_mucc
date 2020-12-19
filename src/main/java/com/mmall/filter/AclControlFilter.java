package com.mmall.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.model.SysUser;
import com.mmall.service.SysCoreService;
import com.mmall.utils.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限点过滤器(用于拦截请求是否有权限访问)
 */
public class AclControlFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Set<String> exclusionUrlSet = Sets.newHashSet();
    private final static String noAuthUrl = "/sys/user/noAuth.page";

    private static List<String> exclusionUrlList = Lists.newArrayList();//用于存放不需要过滤的请求url


    /**
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        exclusionUrlList.add("/sys/user/noAuth.page");
        exclusionUrlList.add("/login.page");
        exclusionUrlList.add("/sys/user/noAuth.page");

        exclusionUrlSet = Sets.newHashSet(exclusionUrlList);

    }

    /**
     * 执行的方法
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;
        //获取请求url
        String contextPath = servletRequest.getServletPath();
        //请求参数
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();


        //排除不需要过滤的权限(判断当前请求url是否在排除的集合中，如果在集合中不进行拦截)
        if (exclusionUrlSet.contains(contextPath)) {
            chain.doFilter(servletRequest, servletResponse );
            return;
        }

        //获取当前用户
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) { //校验用户是否登录
            logger.info("someone visit {}, but no login, parameter:{}", contextPath, JsonMapper.obj2String(parameterMap));
            noAuth(servletRequest, servletResponse);//进入无权限访问方法
            return;
        }

        //获取当前登录用户的是否否有权限访问
        //因为你当前filter没有交给spring进行管理，所以要从spring上下文获取bean
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        //查询当前登录用户的权限url
        boolean exits = sysCoreService.hasUrlAcl(contextPath);
        if (!exits) {//存在该url
            logger.info("{} visit {}, but no login, parameter:{}", JsonMapper.obj2String(sysUser), contextPath, JsonMapper.obj2String(parameterMap));
            noAuth(servletRequest, servletResponse);
            return;
        }


        chain.doFilter(servletRequest, servletResponse);
        return;

    }

    /**
     * 销毁方法
     */
    @Override
    public void destroy() {

    }



    /**
      * @Author gaopeng
      * @Description //无权限方法
      * @Date 14:03 2020/12/19
      * @param
      * @return void
      **/
    public void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();//请求url
        //1、判断请求类型
        if (servletPath.endsWith(".json")) { //请求为.json 的请求
            JsonData jsonData = JsonData.fail("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonMapper.obj2String(jsonData));
            return;
        } else {
            clientRedirect(noAuthUrl, response);
            return;
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }
}
