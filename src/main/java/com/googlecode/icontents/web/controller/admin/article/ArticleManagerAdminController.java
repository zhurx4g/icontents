package com.googlecode.icontents.web.controller.admin.article;

import java.util.ArrayList;
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
import com.googlecode.icontents.bean.component.ArticleComponent;
import com.googlecode.icontents.bean.component.HtmlComponent;
import com.googlecode.icontents.service.ArticleService;
import com.googlecode.icontents.service.CatalogService;
import com.googlecode.icontents.web.ActionsConstant;

@Controller
@RequestMapping(value="admin/article/articleManager.do")
public class ArticleManagerAdminController extends AbstractArticleAdminController{
    
	@Resource
	private CatalogService catalogService;
	@Resource
	private ArticleService articleService;
	
	@Override
	protected ModelAndView handleAdminRequestInternal(
			HttpServletRequest request, HttpServletResponse response,
			User adminUser,Map<String,Object> model) throws Exception {
		model.put("catalogList", catalogService.select(0, Integer.MAX_VALUE));
		
		long articleId = ServletRequestUtils.getLongParameter(request, "articleId", Constants.ERROR);
		int articleLayout = ServletRequestUtils.getIntParameter(request, "articleLayout", Constants.ERROR);
		
		Article article = null;
		
		String action = ServletRequestUtils.getStringParameter(request, ActionsConstant.ACTION, ActionsConstant.GET);

		if(ActionsConstant.GET.equalsIgnoreCase(action)){
			if(articleId!=Constants.ERROR){
				article = articleService.getObjectById(articleId);
				model.put("article", article);
			}
		}else if(ActionsConstant.SAVE.equalsIgnoreCase(action)){
			article = new Article();
			
			Map<String,String> params = getParamMap(request);
			article.parse(params, PREFIX);
			article.setLayout(articleLayout);
			article.setId(articleId);
			
			int componentListLength = ServletRequestUtils.getIntParameter(request, "componentListLength", 0);
			
//			System.out.println("componentListLength:" + componentListLength);
			for(int i=0;i<componentListLength;i++){
				ArticleComponent articleComponent = getArticleComponent(params,i);
//				System.out.println("articleComponent:"+articleComponent);
				article.addComponent(articleComponent);
			}
			
			System.out.println("article:"+article);
			if(articleId==Constants.ERROR){
			    articleService.insert(article);
			    articleId = article.getId();
			}else{
				articleService.update(article);
			}
			
			article = articleService.getObjectById(articleId);
			
			model.put("article", article);
		}
		System.out.println("1111111111111111111111111:"+article);

		if(articleLayout==Constants.ERROR){
		    System.out.println("2222222222222222:"+article);
			if(article!=null){
			    System.out.println("333333333333333333:"+article);
				articleLayout = article.getLayout();
				System.out.println("44444444444444444:"+article);
			}else{
			    System.out.println("55555555555555555555:"+article);
			    articleLayout = Article.LAYOUT_DEFAULT;
			}
			System.out.println("66666666666666666666:"+article);
		}
		System.out.println("777777777777777777777:"+article);
		model.put("articleLayout", articleLayout);
		

		
		System.out.println("articleLayout=================>" + articleLayout);
		logger.error("articleLayout=================>" + articleLayout);
		if(articleLayout==Article.LAYOUT_DEFAULT){
	        List<ArticleComponent> defaultComponentList = new ArrayList<ArticleComponent>();
	        HtmlComponent htmlComponent = new HtmlComponent();
	        htmlComponent.setPosition(Article.LAYOUT_DEFAULT);
	        defaultComponentList.add(htmlComponent);  
		    System.out.println("defaultComponentList=================>" + defaultComponentList);
		    logger.error("defaultComponentList=================>" + defaultComponentList);
		    model.put("defaultComponentList", defaultComponentList);
		    model.put("defaultComponent", htmlComponent);
		}
		
		return new ModelAndView(getViewName(),model);
	}

	@Override
	protected boolean hasAuth(User adminUser) {
		return false;
	}
}
