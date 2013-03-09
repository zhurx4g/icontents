package com.googlecode.icontents.web.controller.admin.article;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.bean.Catalog;
import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.CatalogService;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.AbstractController;

@Controller
@RequestMapping(value="admin/article/catalog")
public class CatalogAdminController extends AbstractController {

	@Resource
	private CatalogService catalogService;
	
	@RequestMapping(value="list.do")
	protected ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			User adminUser,Map<String,Object> model) {
		model.put("date", new Date());
		model.put("tabIndex", ServletRequestUtils.getStringParameter(request, "tabIndex", "0"));
		
		int page = ServletRequestUtils.getIntParameter(request, "page", 0);
		int size = ServletRequestUtils.getIntParameter(request, "size", getSize());
		
		String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION, ActionsConstant.LIST);

		if(ActionsConstant.ADD.equalsIgnoreCase(action)||ActionsConstant.EDIT.equalsIgnoreCase(action)){
			Catalog catalog = new Catalog();
			catalog.parse(getParamMap(request));
			
			catalog.setUpdateUserId(0);
			
			if(ActionsConstant.ADD.equalsIgnoreCase(action)){
				catalog.setCreateUserId(0);
				catalogService.insert(catalog);
			}else{
				catalogService.update(catalog);
			}
		}else if(ActionsConstant.DELETE.equalsIgnoreCase(action)){
			int id = ServletRequestUtils.getIntParameter(request, "id", -1);
			catalogService.removeObjectById(id);
		}else if(ActionsConstant.ADD.equalsIgnoreCase(action)){
		}else if(ActionsConstant.ADD.equalsIgnoreCase(action)){
		}else if(ActionsConstant.ADD.equalsIgnoreCase(action)){
			
		}
		
		model.put("catalogList", catalogService.select(page, size));
		return new ModelAndView("admin/article/catalog",model);
	}
}
