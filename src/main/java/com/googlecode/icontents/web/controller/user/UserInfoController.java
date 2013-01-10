package com.googlecode.icontents.web.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.util.JacksonUtils;
import com.googlecode.icontents.web.controller.AbstractContentController;

@Controller
@RequestMapping(value="user/userInfo.do")
public class UserInfoController extends AbstractContentController {

	@Resource
	private UserService userService;
	
	@Override
	protected ModelAndView handleIndexRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		model.put("result", false);
		
		String password = ServletRequestUtils.getStringParameter(request, "password","");
		String newPassword = ServletRequestUtils.getStringParameter(request, "newPassword","");
		
		if(loginUser==null){
			model.put("error", "用户已经退出登录");
			
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		if(!DigestUtils.md5Hex(password).equals(loginUser.getPassword())){
			model.put("error", "密码不正确");
			
			model.put("json", JacksonUtils.toString(model));
			return new ModelAndView(getViewName(),model);
		}
		
		userService.updateUserPassword(loginUser.getId(), DigestUtils.md5Hex(newPassword));
		
		model.put("result", true);
		
		model.put("json", JacksonUtils.toString(model));
		return new ModelAndView(getViewName(),model);
	}

	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("root")));
	}
}
