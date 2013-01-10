package com.googlecode.icontents.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * 后台管理控制器的基类，完成共同的权限验证
 * 错误页面跳转，未开通权限页面跳转等功能。
 * @author zhurx
 *
 */
public abstract class AbstractContentController extends ParameterizableViewController {
    
    @Override
    protected final ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Map<String,Object> model = new HashMap<String,Object>();
    	model.put("contextPath", request.getContextPath());
    	return handleIndexRequestInternal(request,response,model);
    }
    
    protected String getString(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }
    
    protected String getFilteredString(HttpServletRequest request, String key,String defaultValue) {
        if(request==null){
            return defaultValue;
        }
        
        String result = ServletRequestUtils.getStringParameter(request,key, defaultValue);
        if(result==null){
            return result;
        }
        result =result.replaceAll("\n", "<br/>");
        result =result.replaceAll("\r", "<br/>");
        result =result.replaceAll("'", "");
        result =result.replaceAll("\"", "“");
        return result;
    }
    protected String getFilteredHTML(HttpServletRequest request, String key,String defaultValue) {
        if(request==null){
            return defaultValue;
        }
        String result = ServletRequestUtils.getStringParameter(request,key, defaultValue);
        if(result==null){
            return defaultValue;
        }
        result =result.replaceAll("\n", "");
        result =result.replaceAll("\r", "");
        result =result.replaceAll("\"", "'");
        return result;
    }
    protected String getFilteredString(HttpServletRequest request, String key) {
        return getFilteredString(request,key,null);
    }
    protected String getFilteredHTML(HttpServletRequest request, String key) {
        return getFilteredHTML(request,key,null);
    }

    protected long getLong(HttpServletRequest request, String key) {
        return Long.parseLong(request.getParameter(key));
    }
    
    protected boolean getBoolean(HttpServletRequest request, String key) {
        return Boolean.parseBoolean(request.getParameter(key));
    }
    
    protected int parseInt(String valueStr,int defaultValue){
        try{
            return Integer.parseInt(valueStr);
        }catch(Exception e){
        }
        return defaultValue;
    }
    
    /**
     * 处理管理页面的业务逻辑处理,返回一个数据模型,使用方法共和普通的Controller一样
     * @param request
     * @param response
     * @param adminUser
     * @return
     * @throws Exception
     */
    protected abstract ModelAndView handleIndexRequestInternal(HttpServletRequest request,
            HttpServletResponse response,Map<String,Object> model) throws Exception;
    
    public Map<String,String> getParamMap(HttpServletRequest request){
        Map<String,String> dataMap = new HashMap<String,String>();
        
        Enumeration<?> enumer = request.getParameterNames();
        
        while(enumer.hasMoreElements()){
            String key = (String)enumer.nextElement();
            String value = request.getParameter(key);
            dataMap.put(key, value==null?"":value.trim());
        }
        
        return dataMap;
    }
}
