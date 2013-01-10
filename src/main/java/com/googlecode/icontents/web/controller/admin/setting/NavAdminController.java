package com.googlecode.icontents.web.controller.admin.setting;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.Nav;
import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.NavService;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/setting/navAdmin.do")
public class NavAdminController extends AbstractAdminController {

    @Resource
    private NavService navService;

    @Override
    protected ModelAndView handleAdminRequestInternal(HttpServletRequest request, HttpServletResponse response,
            User adminUser, Map<String, Object> model) throws Exception {
        model.put("date", new Date());
        model.put("tabIndex", ServletRequestUtils.getStringParameter(request, "tabIndex", "0"));
        
        int page = ServletRequestUtils.getIntParameter(request, "page", 0);
        int size = ServletRequestUtils.getIntParameter(request, "size", getSize());
        
        String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION, ActionsConstant.LIST);

        if(ActionsConstant.ADD.equalsIgnoreCase(action)||ActionsConstant.EDIT.equalsIgnoreCase(action)){
            Nav nav = new Nav();
            nav.parse(getParamMap(request));
            
            nav.setUpdateUserId(0);
            
            if(ActionsConstant.ADD.equalsIgnoreCase(action)){
                nav.setCreateUserId(0);
                navService.insert(nav);
            }else{
                navService.update(nav);
            }
        }else if(ActionsConstant.DELETE.equalsIgnoreCase(action)){
            int id = ServletRequestUtils.getIntParameter(request, "id", -1);
            navService.removeObjectById(id);
        }
        
        model.put("navList", navService.select(page, size));
        return new ModelAndView(getViewName(),model);
    }
    
    @Override
    protected boolean hasAuth(User adminUser) {
        return true;
    }
}
