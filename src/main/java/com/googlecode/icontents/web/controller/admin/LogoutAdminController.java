package com.googlecode.icontents.web.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.googlecode.icontents.bean.User;

/**
 * 登录控制页面
 * @author zhurx
 *
 */
@Controller
@RequestMapping(value="admin/logout.do")
public class LogoutAdminController extends AbstractAdminController{
	
	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser,Map<String,Object> model) throws Exception {
		request.getSession().removeAttribute("loginUser");
		String contextPath = (String)model.get("contextPath");
		return new ModelAndView(new RedirectView(contextPath + ADMIN_LOGIN_URL));
	}

	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}
}
