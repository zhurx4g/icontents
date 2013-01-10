package com.googlecode.icontents.web.controller.admin.article;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.icontents.Constants;
import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.service.ArticleService;
import com.googlecode.icontents.service.CatalogService;
import com.googlecode.icontents.web.ActionsConstant;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

@Controller
@RequestMapping(value="admin/article/articleList.do")
public class ArticleListAdminController extends AbstractAdminController {

	@Resource
	private ArticleService articleService;
	@Resource
	private CatalogService catalogService;
	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser,Map<String,Object> model) throws Exception {
		int page = ServletRequestUtils.getIntParameter(request, "page", 1);
		int size = ServletRequestUtils.getIntParameter(request, "size", getSize());
		
		model.put("catalogList", catalogService.select(0, Integer.MAX_VALUE));
		String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION, ActionsConstant.LIST);
		
		if(ActionsConstant.DELETE.equalsIgnoreCase(action)){
		    long articleId = ServletRequestUtils.getLongParameter(request, "articleId", Constants.ERROR);
		    
		    System.out.println("articleId:"+articleId);
		    articleService.removeObjectById(articleId);
		}
		List<Article> articleList = articleService.select(page, size);
		model.put("articleList", articleList);
		
		
		if(page>1){
			model.put("prePage", page-1);
		}
        if(articleList!=null&&articleList.size()>0){
            int total = articleService.getTotal();
            if(articleList.size()==size){
                if(page==(total/size)){
                    if(total%size!=0){
                        model.put("nextPage", page+1);
                    }
                }else{
                    model.put("nextPage", page+1);
                }
            }
            model.put("lastpage", total/size+(total%size==0?0:1));
        }
        
		return new ModelAndView(getViewName(),model);
	}

	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}

}
