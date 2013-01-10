package com.googlecode.icontents.web.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.googlecode.icontents.web.controller.AbstractContentController;

/**
 * 登录控制页面
 * @author zhurx
 *
 */
@Controller
@RequestMapping(value="user/logout.do")
public class LogoutController extends AbstractContentController{
	
	@Override
	protected ModelAndView handleIndexRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		request.getSession().removeAttribute("loginUser");
		String contextPath = (String)model.get("contextPath");
		return new ModelAndView(new RedirectView(contextPath+"/"));
	}
}
