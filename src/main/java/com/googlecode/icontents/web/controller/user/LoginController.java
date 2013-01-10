package com.googlecode.icontents.web.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.util.JacksonUtils;
import com.googlecode.icontents.web.controller.AbstractContentController;

@Controller
@RequestMapping(value="user/login.do")
public class LoginController extends AbstractContentController {

	@Resource
	private UserService userService;
	
	@Override
	protected ModelAndView handleIndexRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		String contextPath = (String)model.get("contextPath");
		
		if(request.getSession().getAttribute("loginUser")!=null){
			model.put("result", true);
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		
		String userName = ServletRequestUtils.getStringParameter(request, "username", "");
		String password = ServletRequestUtils.getStringParameter(request, "password", "");
		String vcode = ServletRequestUtils.getStringParameter(request, "vcode", "");
		
		if(StringUtils.isBlank(userName)){
			model.put("error", "请输入用户名");
			model.put("result", false);
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		
		if(StringUtils.isBlank(password)){
			model.put("error", "请输入密码");
			model.put("result", false);
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		
		String svcode = (String)request.getSession().getAttribute("vcode");
		if(StringUtils.isBlank(svcode)){
			model.put("error", "验证码已经过期");
			model.put("result", false);
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		if(StringUtils.isBlank(vcode)){
			model.put("error", "请输入验证码");
			model.put("result", false);
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		if(!svcode.equalsIgnoreCase(vcode)){
			model.put("error", "验证码错误");
			model.put("result", false);
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		
		User loginUser = userService.getUserByUserName(userName);
		
		if(loginUser==null){
			model.put("error", "用户不存在或者密码错误");
			model.put("result", false);
			model.put("json", JacksonUtils.toString(model));
		}else{
			if(DigestUtils.md5Hex(password).equals(loginUser.getPassword())){
				request.getSession().setAttribute("loginUser", loginUser);
				model.put("result", true);
				model.put("json", JacksonUtils.toString(model));
			}else{
				model.put("error", "用户不存在或者密码错误");
				model.put("result", false);
				model.put("json", JacksonUtils.toString(model));
			}
		}
		
		return new ModelAndView(getViewName(),model);
	}
}
