package com.googlecode.module.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.util.JacksonUtils;
import com.googlecode.module.AbstractViewModule;
import com.googlecode.module.WebModuleContext;

public class JsonViewModule extends AbstractViewModule {

    @Override
    public ModelAndView handler(WebModuleContext context, ModelMap model) {
        HttpServletResponse response = context.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Object object = model.get(SINGLE_MODLE);
        
        PrintWriter writer = getWriter(response);

        if(object!=null){
            writer.print(JacksonUtils.toString(object));            
        }else{
            writer.print(JacksonUtils.toString(model));            
        }
        return null;
    }

}
