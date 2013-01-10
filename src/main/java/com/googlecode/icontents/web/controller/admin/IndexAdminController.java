package com.googlecode.icontents.web.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;

@Controller
@RequestMapping(value="admin/index.do")
public class IndexAdminController extends AbstractAdminController {

	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser,Map<String,Object> model) throws Exception {
		model.put("tabIndex", ServletRequestUtils.getStringParameter(request, "tabIndex", "0"));
		return new ModelAndView(getViewName(),model);
	}

	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}

}
