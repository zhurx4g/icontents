package com.googlecode.icontents.web.controller.admin;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.RedirectView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.util.JacksonUtils;

/**
 * 后台管理控制器的基类，完成共同的权限验证
 * 错误页面跳转，未开通权限页面跳转等功能。
 * @author zhurx
 *
 */
public abstract class AbstractAdminController extends ParameterizableViewController {
    
    /**
     * 后台用户Service
     */
    private UserService adminUserService;
    
    /**
     * 默认列表页的页面大小
     */
    private int size = 20;
    
    public static final String ADMIN_INDEX_URL = "/admin/index.html";
    public static final String ADMIN_LOGIN_URL = "/admin/login.html";
    
    private String errorViewName = "admin/error";
    
    @Override
    protected final ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	User loginUser = (User)request.getSession().getAttribute("loginUser");
    	String contextPath = request.getContextPath();
    	if(loginUser==null&&!(this instanceof LoginAdminController)){
    		
    		if(!(this instanceof LogoutAdminController)){
    			return new ModelAndView(new RedirectView(contextPath + ADMIN_LOGIN_URL));
    		}
    	}
    	
    	if(loginUser!=null&&loginUser.getLevel()==0){
    		return new ModelAndView(new RedirectView(contextPath+"/"));
    	}
    	Map<String,Object> model = new HashMap<String,Object>();
    	model.put("contextPath", contextPath);
    	
    	try{
    		return handleAdminRequestInternal(request,response,loginUser,model);
    	}catch(Exception e){
    		e.printStackTrace();
    		model.put("e", e);
    		model.put("json", JacksonUtils.toString(model));
    		return new ModelAndView(getErrorViewName(),model);
    	}
    }
    
    protected String getString(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }
    
    protected String getFilteredString(HttpServletRequest request, String key,String defaultValue) {
        if(request==null){
            return defaultValue;
        }
        
        String result = ServletRequestUtils.getStringParameter(request,key, defaultValue);
        if(result==null){
            return result;
        }
        result =result.replaceAll("\n", "<br/>");
        result =result.replaceAll("\r", "<br/>");
        result =result.replaceAll("'", "");
        result =result.replaceAll("\"", "“");
        return result;
    }
    protected String getFilteredHTML(HttpServletRequest request, String key,String defaultValue) {
        if(request==null){
            return defaultValue;
        }
        String result = ServletRequestUtils.getStringParameter(request,key, defaultValue);
        if(result==null){
            return defaultValue;
        }
        result =result.replaceAll("\n", "");
        result =result.replaceAll("\r", "");
        result =result.replaceAll("\"", "'");
        return result;
    }
    protected String getFilteredString(HttpServletRequest request, String key) {
        return getFilteredString(request,key,null);
    }
    protected String getFilteredHTML(HttpServletRequest request, String key) {
        return getFilteredHTML(request,key,null);
    }

    protected long getLong(HttpServletRequest request, String key) {
        return Long.parseLong(request.getParameter(key));
    }
    
    protected boolean getBoolean(HttpServletRequest request, String key) {
        return Boolean.parseBoolean(request.getParameter(key));
    }
    
    protected int parseInt(String valueStr,int defaultValue){
        try{
            return Integer.parseInt(valueStr);
        }catch(Exception e){
        }
        return defaultValue;
    }
    /**
     * 用户有没有页面访问权限
     * @param adminUser
     * @return
     */
    protected abstract boolean hasAuth(User adminUser);
    
    /**
     * 处理管理页面的业务逻辑处理,返回一个数据模型,使用方法共和普通的Controller一样
     * @param request
     * @param response
     * @param adminUser
     * @return
     * @throws Exception
     */
    protected abstract ModelAndView handleAdminRequestInternal(HttpServletRequest request,
            HttpServletResponse response,User adminUser,Map<String,Object> model) throws Exception;
    
    public UserService getAdminUserService() {
        return adminUserService;
    }

    public void setAdminUserService(UserService adminUserService) {
        this.adminUserService = adminUserService;
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,String> getParamMap(HttpServletRequest request){
        Map<String,String> dataMap = new HashMap<String,String>();
        
        Enumeration<String> enumer = request.getParameterNames();
        
        while(enumer.hasMoreElements()){
            String key = enumer.nextElement();
            String value = request.getParameter(key);
            dataMap.put(key, value==null?"":value.trim());
        }
        
        return dataMap;
    }

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getErrorViewName() {
		return errorViewName;
	}

	public void setErrorViewName(String errorViewName) {
		this.errorViewName = errorViewName;
	}
}
