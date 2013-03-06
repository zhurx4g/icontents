package com.googlecode.icontents.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.googlecode.icontents.bean.User;

@Component("loginAdminInterceptor")
public class LoginAdminInterceptor extends AbstraceHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath = request.getContextPath();
        
        String adminPathPrefix = contextPath +"/admin/";
        
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith(adminPathPrefix)){
            if(requestURI.equals(contextPath + "/admin/login.do")){
                return true;
            }
            Object userObj = request.getSession().getAttribute("loginAdminUser");
            if(userObj!=null&&userObj instanceof User){
                return true;
            }
            response.sendRedirect(contextPath + "/admin/login.html");
            return false;
        }
        return true;
    }
}
