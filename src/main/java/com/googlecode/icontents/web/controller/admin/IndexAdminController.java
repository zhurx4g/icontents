package com.googlecode.icontents.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.web.controller.AbstractController;

@Controller
@RequestMapping(value="admin/")
public class IndexAdminController extends AbstractController {
	
    @RequestMapping(value="index.do", method=RequestMethod.GET)
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.put("tabIndex", ServletRequestUtils.getStringParameter(request, "tabIndex", "0"));
        return new ModelAndView("admin/index",model);
    }
}
