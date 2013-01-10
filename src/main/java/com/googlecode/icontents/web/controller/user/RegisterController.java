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
@RequestMapping(value="user/register.do")
public class RegisterController extends AbstractContentController {

	@Resource
	private UserService userService;
	
	@Override
	protected ModelAndView handleIndexRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		User user = new User();
		user.parse(getParamMap(request));
		
		model.put("result", false);
		
		if(!procParam(request,"vcode","请输入验证码",model)){
			return new ModelAndView(getViewName(),model);
		}
		if(!procParam(request,"userName","请输入用户名",model)){
			return new ModelAndView(getViewName(),model);
		}
		if(!procParam(request,"email","请输入email",model)){
			return new ModelAndView(getViewName(),model);
		}
		if(!procParam(request,"password","请输入密码",model)){
			String value = ServletRequestUtils.getStringParameter(request, "password","");
			user.setPassword(DigestUtils.md5Hex(value));
			return new ModelAndView(getViewName(),model);
		}
		
		String username = ServletRequestUtils.getStringParameter(request, "userName","");
		String email = ServletRequestUtils.getStringParameter(request, "email","");
		if(userService.isExist(username, email)){
			model.put("error", "用户名或者email已经注册");
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		
		user.setLevel(0);
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userService.insert(user);
		
		model.put("result", true);
		
		model.put("json", JacksonUtils.toString(model));
		return new ModelAndView(getViewName(),model);
	}
	
	private boolean procParam(HttpServletRequest request,String name,String error,Map<String, Object> model){
		String value = ServletRequestUtils.getStringParameter(request, name,"");
		
		if(StringUtils.isBlank(value)){
			model.put("error", error);
			model.put("json", JacksonUtils.toString(model));
			return false;
		}
		return true;
	}
}
