package com.googlecode.icontents.web.controller.admin.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.bean.resource.UplInfo;
import com.googlecode.icontents.bean.resource.UploadMonitor;
import com.googlecode.icontents.util.JacksonUtils;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/resource/resourceUpload.do")
public class ResourceUploadAdminController extends AbstractAdminController{

	String tempdir = ".";
    @Override
    protected ModelAndView handleAdminRequestInternal(
            HttpServletRequest request, HttpServletResponse response,
            User adminUser,Map<String,Object> model) throws Exception {
    	String fname = request.getParameter("uplMonitor");
    	System.out.println(fname+"===>");
    	System.out.println("===>"+JacksonUtils.toString(UploadMonitor.uploadTable));
		//First opening
		boolean first = false;
		if (request.getParameter("first") != null)
			first = true;
		UplInfo info = new UplInfo();
		if (!first) {
			info = UploadMonitor.getInfo(fname);
			if (info == null) {
				//Windows
				int posi = fname.lastIndexOf("\\");
				if (posi != -1) info = UploadMonitor.getInfo(fname.substring(posi + 1));
			}
		}
		if (info==null){
			UploadMonitor.remove(fname);
		}else if (info.totalSize != info.currSize || info.currSize == 0) {
			
		}else{			
			UploadMonitor.remove(fname);
		}
		model.put("info", info);
		model.put("fname", fname);
		model.put("dir", null);
        return new ModelAndView(getViewName(),model);
    }

    public static String encode(String path){
        try {
            return URLEncoder.encode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }
    @Override
    protected boolean hasAuth(User adminUser) {
        return false;
    }

}
