package com.googlecode.icontents.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.googlecode.utils.ImageUtils;

public class ImageController implements Controller {

	private Log logger = LogFactory.getLog(getClass());
	private static final String IMAGE_HOME = "images/";
	private static String imageHome = null;
	
	@Override
	@RequestMapping("/image.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = ServletRequestUtils.getStringParameter(request, "name", "null");
		if(StringUtils.isEmpty(name)){
			return defaultResponse(response);
		}
		if(name.contains("..")){
			return defaultResponse(response);
		}
		if(imageHome==null) {
			imageHome = request.getServletPath() + "/" + IMAGE_HOME;
		}
		
		int width = ServletRequestUtils.getIntParameter(request, "w", 0);
		int height = ServletRequestUtils.getIntParameter(request, "h", 0);
		
		BufferedImage image = null;
		logger.error("===============" + imageHome + name);
		try{
			image = ImageUtils.zoom(ImageIO.read(new File(imageHome + name)), width, height);
			ImageIO.write(image, "png", response.getOutputStream());
			return null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return defaultResponse(response);
	}

	private ModelAndView defaultResponse(HttpServletResponse response) {
		return null;
	}
}
