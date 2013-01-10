package com.googlecode.icontents.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

@Controller
@RequestMapping(value="error.do")
public class ErrorController extends ParameterizableViewController {

	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("contextPath", request.getContextPath());
		
		return new ModelAndView(getViewName());
	}
}
