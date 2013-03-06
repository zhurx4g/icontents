package com.googlecode.icontents.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.web.controller.AbstractController;

/**
 * 登录控制页面
 * @author zhurx
 *
 */
@Controller
@RequestMapping(value="admin/")
public class LoginAdminController extends AbstractController{

	@Resource
	private UserService userService;
	
    @RequestMapping(value="login.do", method=RequestMethod.GET)
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return new ModelAndView(getViewName(),model);
    }
    @RequestMapping(value="login.do", method=RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String contextPath = request.getContextPath();
		
		if(request.getSession().getAttribute("loginUser")!=null){
			return new ModelAndView(new RedirectView(contextPath + "/admin/index.html"));
		}
		
		String userName = ServletRequestUtils.getStringParameter(request, "username", "");
		String password = ServletRequestUtils.getStringParameter(request, "password", "");
		String vcode = ServletRequestUtils.getStringParameter(request, "vcode", "");
		
		if(StringUtils.isBlank(userName)){
			return new ModelAndView(getViewName(),model);
		}
		
		if(StringUtils.isBlank(password)){
			model.put("error", "请输入密码");
			return new ModelAndView(getViewName(),model);
		}
		
		String svcode = (String)request.getSession().getAttribute("vcode");
		if(StringUtils.isBlank(svcode)){
			model.put("error", "验证码已经过期");
			return new ModelAndView(getViewName(),model);
		}
		if(StringUtils.isBlank(vcode)){
			model.put("error", "请输入验证码");
			return new ModelAndView(getViewName(),model);
		}
		if(!svcode.equalsIgnoreCase(vcode)){
			model.put("error", "验证码错误");
			return new ModelAndView(getViewName(),model);
		}
		
		User loginUser = userService.getUserByUserName(userName);
		
		if(loginUser==null){
			model.put("error", "用户不存在或者密码错误");
		}else{
			if(DigestUtils.md5Hex(password).equals(loginUser.getPassword())){
				request.getSession().setAttribute("loginAdminUser", loginUser);
				
				if(loginUser.getLevel()==1){
					return new ModelAndView(new RedirectView(contextPath + "/admin/index.html"));
				}else{
					return new ModelAndView(new RedirectView(contextPath + "/"));
				}
			}else{
				model.put("error", "用户不存在或者密码错误");
			}
		}
		
		return new ModelAndView(getViewName(),model);
	}
	
    @RequestMapping(value="logout.do")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().removeAttribute("loginAdminUser");
        String contextPath = request.getContextPath();
        return new ModelAndView(new RedirectView(contextPath + "/admin/login.html"));
    }
    
	private String getViewName() {
        return "admin/login";
    }
}
