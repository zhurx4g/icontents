package com.googlecode.icontents.web.controller.admin;

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
import org.springframework.web.servlet.view.RedirectView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;

/**
 * 登录控制页面
 * @author zhurx
 *
 */
@Controller
@RequestMapping(value="admin/login.do")
public class LoginAdminController extends AbstractAdminController{

	@Resource
	private UserService userService;
	
	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser,Map<String,Object> model) throws Exception {
		String contextPath = (String)model.get("contextPath");
		
		if(request.getSession().getAttribute("loginUser")!=null){
			return new ModelAndView(new RedirectView(contextPath + ADMIN_INDEX_URL));
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
		    System.out.println("password:" + DigestUtils.md5Hex(password));
			if(DigestUtils.md5Hex(password).equals(loginUser.getPassword())){
				request.getSession().setAttribute("loginUser", loginUser);
				
				if(loginUser.getLevel()==1){
					return new ModelAndView(new RedirectView(contextPath + ADMIN_INDEX_URL));
				}else{
					return new ModelAndView(new RedirectView(contextPath + "/"));
				}
			}else{
				model.put("error", "用户不存在或者密码错误");
			}
		}
		
		return new ModelAndView(getViewName(),model);
	}

	public static void main(String[] args) {
	    System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("root")));
        System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("qwertyuiop")));
    }
	
	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}
}
