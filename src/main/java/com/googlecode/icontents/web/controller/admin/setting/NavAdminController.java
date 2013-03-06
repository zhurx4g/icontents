package com.googlecode.icontents.web.controller.admin.setting;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.Nav;
import com.googlecode.icontents.service.NavService;
import com.googlecode.icontents.web.controller.AbstractController;

@Controller
@RequestMapping(value="admin/setting/nav/")
public class NavAdminController extends AbstractController {

    @Resource
    private NavService navService;

    @RequestMapping(value="list.do", method=RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.put("date", new Date());
        model.put("tabIndex", ServletRequestUtils.getStringParameter(request, "tabIndex", "0"));
        
        int page = ServletRequestUtils.getIntParameter(request, "page", 0);
        int size = ServletRequestUtils.getIntParameter(request, "size", getSize(request));
        
        List<Nav> navList = navService.select(page, size);
        model.addAttribute("navList", navList);
        model.addAttribute("viewName", "admin/setting/nav/list");
        return new ModelAndView(getCommonViewName(),model);
    }
    @RequestMapping(value="get.do", method=RequestMethod.GET)
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        int navId = ServletRequestUtils.getIntParameter(request, "navId", 0);
        model.addAttribute("nav", navService.getObjectById(navId));
        return list(request, response, model);
    }
    @RequestMapping(value="add.do", method=RequestMethod.POST)
    public ModelAndView add(Nav nav, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        nav.setCreateUserId(0);
        navService.insert(nav);
        return list(request, response, model);
    }
    
    @RequestMapping(value="remove.do", method=RequestMethod.GET)
    public ModelAndView remove(Nav nav, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        int id = ServletRequestUtils.getIntParameter(request, "id", -1);
        navService.removeObjectById(id);
        return list(request, response, model);
    }
    @RequestMapping(value="update.do", method=RequestMethod.POST)
    public ModelAndView update(Nav nav, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        navService.update(nav);
        return list(request, response, model);
    }
}
