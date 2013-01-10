package com.googlecode.icontents.web.module.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.module.Module;
import com.googlecode.module.WebModuleContext;

@Component
public class ResourceModule implements Module {

    @Override
    public ModelAndView handler(WebModuleContext context, ModelMap model) {
        HttpServletRequest request = context.getRequest();
        model.addAttribute("contextPath", request.getContextPath());
        return null;
    }

}
