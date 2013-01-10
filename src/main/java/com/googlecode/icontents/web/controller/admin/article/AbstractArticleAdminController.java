package com.googlecode.icontents.web.controller.admin.article;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.googlecode.icontents.bean.component.ArticleComponent;
import com.googlecode.icontents.web.controller.admin.AbstractAdminController;

/**
 * 后台管理控制器的基类，完成共同的权限验证
 * 错误页面跳转，未开通权限页面跳转等功能。
 * @author zhurx
 *
 */
public abstract class AbstractArticleAdminController extends AbstractAdminController {
	protected static String NULL_PREFIX = "";
	protected static String PREFIX = "article";
	protected static String COMPONENT_TYPE = "componentType";
	
	protected ArticleComponent getArticleComponent(Map<String,String> params,int index){
		String componentType = params.get(COMPONENT_TYPE+index);
		
		System.out.println("componentType"+index+":"+componentType);
		ArticleComponent articleComponent = null;
		if(StringUtils.isNotBlank(componentType)){
			try {
				articleComponent = (ArticleComponent) Class.forName(ArticleComponent.class.getPackage().getName()+"."+componentType).newInstance();
				articleComponent.parse(params, NULL_PREFIX, index);
				articleComponent.setComponentType(componentType);
				
				System.out.println("articleComponent:"+articleComponent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return articleComponent;
	}
}
