package com.googlecode.icontents.web.controller.admin.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.UserService;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/user/user.do")
public class UserAdminController extends AbstractAdminController {
	
	@Resource
	private UserService userService;
	
	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser, Map<String, Object> model) throws Exception {
		int page = ServletRequestUtils.getIntParameter(request, "page", 0);
		
		List<User> userList = null;
		int total = -1;
		
		String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION,ActionsConstant.LIST);
		
		if(ActionsConstant.SEARCH.equalsIgnoreCase(action)){
			String email = ServletRequestUtils.getStringParameter(request, "email","");
			userList = userService.searchUser(email);
			total = userService.searchUserCount(email);
		}else if(ActionsConstant.DELETE.equalsIgnoreCase(action)){
			int id = ServletRequestUtils.getIntParameter(request, "id", 0);
			
			userService.removeObjectById(id);
		}else if(ActionsConstant.UPDATE.equalsIgnoreCase(action)){
			int id = ServletRequestUtils.getIntParameter(request, "id", 0);
			int level = ServletRequestUtils.getIntParameter(request, "level", 0);
			
			userService.updateUserLevel(id, level);
		}
		
		if(total==-1){
			total = userService.getTotal();
		}
		model.put("total", total);
		
		if(userList==null){
			userList = userService.select(page, getSize());
		}
		model.put("userList", userList);
		
		if(page>1){
			model.put("prePage", page-1);
		}
        if(userList!=null&&userList.size()>0){
            if(userList.size()==getSize()){
                if(page==(total/getSize())){
                    if(total%getSize()!=0){
                        model.put("nextPage", page+1);
                    }
                }else{
                    model.put("nextPage", page+1);
                }
            }
            model.put("lastpage", total/getSize()+(total%getSize()==0?0:1));
        }
        
		return new ModelAndView(getViewName(),model);
	}
	
	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}
}
