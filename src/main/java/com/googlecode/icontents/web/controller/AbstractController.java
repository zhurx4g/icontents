package com.googlecode.icontents.web.controller;

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

    public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
