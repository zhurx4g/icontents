package com.googlecode.icontents.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component("initInterceptor")
public class InitInterceptor extends AbstraceHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //request.setAttribute("contextPath", request.getContextPath());
        return true;
    }
}
