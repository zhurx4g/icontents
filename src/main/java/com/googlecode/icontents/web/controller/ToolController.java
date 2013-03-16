package com.googlecode.icontents.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.googlecode.utils.DecompileJs;

@Controller
public class ToolController {
	@RequestMapping(value="/unpack.do")
	public ModelAndView unpack(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String content = ServletRequestUtils.getStringParameter(request, "content");
		if(StringUtils.isNotBlank(content)){
			content = DecompileJs.decompileJs(content);
			model.put("content", HtmlUtils.htmlEscape(content));
		}
		return new ModelAndView("unpack", model);
	}
}
