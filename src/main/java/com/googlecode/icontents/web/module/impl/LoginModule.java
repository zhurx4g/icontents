package com.googlecode.icontents.web.module.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.web.module.AbstractLogicModule;
import com.googlecode.module.WebModuleContext;

@Component
public class LoginModule extends AbstractLogicModule {

    @Autowired
    private UserService userService;
    
    @Override
    public ModelAndView handler(WebModuleContext context, ModelMap model) {
        // HttpServletResponse response = context.getResponse();
        ModelAndView modelAndView = context.getModelAndView();
        String username = paramString(context.getRequest(), "username");
        String password = paramString(context.getRequest(), "password");

        String refer = context.getRequest().getHeader("Referer");
        model.addAttribute("refer", refer);

        modelAndView.setViewName("login");
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            User user = userService.getUserByUserName(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    context.getRequest().getSession().setAttribute("loginUser", user);
                    return new ModelAndView(new RedirectView(
                            StringUtils.isBlank(refer) || refer.contains("login.html") ? context.getRequest().getContextPath() + "/"
                                    : refer));
                } else {
                    model.addAttribute("error", "密码错误");
                }
            } else {
                model.addAttribute("error", "用户名错误");
            }
        }
        return null
        ;
    }
}
