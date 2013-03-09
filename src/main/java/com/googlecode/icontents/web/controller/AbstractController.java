package com.googlecode.icontents.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AbstractController implements ApplicationContextAware {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    public static final String LOGIN_USER = "loginUser";
    
    public static final int SIZE = 10;
    public static final int TOPIC_SIZE = 30;
    
    private ApplicationContext applicationContext;

    public int getSize(HttpServletRequest request){
        return SIZE;
    }
    public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
	
	public String getCommonViewName(){
	    return "admin/common";
	}
	
    @SuppressWarnings("unchecked")
	public Map<String,String> getParamMap(HttpServletRequest request){
        Map<String,String> dataMap = new HashMap<String,String>();
        
        Enumeration<String> enumer = request.getParameterNames();
        
        while(enumer.hasMoreElements()){
            String key = enumer.nextElement();
            String value = request.getParameter(key);
            dataMap.put(key, value==null?"":value.trim());
        }
        
        return dataMap;
    }
    
    private int size = 20;
    
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
