package com.googlecode.icontents.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.googlecode.icontents.Constants;
import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.bean.Catalog;
import com.googlecode.icontents.bean.component.ArticleComponent;
import com.googlecode.icontents.bean.component.ArticleListComponent;
import com.googlecode.icontents.bean.component.CatalogArticleListComponent;
import com.googlecode.icontents.bean.component.QuickNavComponent;
import com.googlecode.icontents.bean.component.SiteMapComponent;
import com.googlecode.icontents.dao.ArticleComponentMapper;
import com.googlecode.icontents.dao.ArticleMapper;
import com.googlecode.icontents.service.ArticleService;
import com.googlecode.icontents.service.CatalogService;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {
	Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private ArticleMapper articleMapper;
	@Resource
	private ArticleComponentMapper articleComponentMapper;
	
	@Resource
	private CatalogService catalogService;
	
	@Override
	public void setBaseDaoHandlewired() {
		setBaseDao(articleMapper);
	}

	@Override
	public int insert(Article article) {
		if(article==null){
			return Constants.ERROR;
		}
		
		int result = super.insert(article);
		List<ArticleComponent> componentList = null;
		
		//header
		componentList = article.getHeaderComponentList();
		insertComponentList(componentList,article);
		//left
		componentList = article.getLeftComponentList();
		insertComponentList(componentList,article);
		//right
		componentList = article.getRightComponentList();
		insertComponentList(componentList,article);
		//footer
		componentList = article.getFooterComponentList();
		insertComponentList(componentList,article);
		//body
		componentList = article.getBodyComponentList();
		insertComponentList(componentList,article);
		
		return result;
	}
	
	public Article getObjectById(long articleId){
		return getObjectById(articleId,false);
	}
	
	public Article getObjectById(long articleId,boolean hasExtInfo){
		Article article = articleMapper.getObjectById(articleId);
		
		//articleComponentDao
		if(article==null){
		    return null;
		}
		
		long componentArticleId = articleId;
		
		Catalog catalog = catalogService.getObjectById(article.getCatalogId());
		article.setCatalog(catalog.getName());
		
		if(hasExtInfo&&article.getLayout()==Article.LAYOUT_DEFAULT) {
		    
		    long templateArticleId = catalog.getTemplateArticleId();
		    
		    if(templateArticleId!=-1&&templateArticleId!=0){
		        Article templateArticle = articleMapper.getObjectById(templateArticleId);
		        if(templateArticle!=null){
		            componentArticleId = templateArticle.getId();
		            article.setLayout(templateArticle.getLayout());
		        }		    
		     }
		}
		article.setHeaderComponentList(processComponentList(articleComponentMapper.selectByArticleId(componentArticleId, ArticleComponent.POSITION_HEADER),hasExtInfo));
		article.setLeftComponentList(processComponentList(articleComponentMapper.selectByArticleId(componentArticleId, ArticleComponent.POSITION_LEFT),hasExtInfo));
		article.setRightComponentList(processComponentList(articleComponentMapper.selectByArticleId(componentArticleId, ArticleComponent.POSITION_RIGHT),hasExtInfo));
		article.setFooterComponentList(processComponentList(articleComponentMapper.selectByArticleId(componentArticleId, ArticleComponent.POSITION_FOOTER),hasExtInfo));
		article.setBodyComponentList(processComponentList(articleComponentMapper.selectByArticleId(articleId, ArticleComponent.POSITION_BODY),hasExtInfo));		    
		
		return article;
	}
	private void insertComponentList(List<ArticleComponent> componentList,Article article){
		for(ArticleComponent articleComponent:componentList){
			articleComponent.setArticleId(article.getId());
			System.out.println(articleComponent);
			articleComponentMapper.insert(articleComponent);
		}
	}

	@Override
	public void update(Article article) {
		super.update(article);
		
		articleComponentMapper.removeByArticleId(article.getId());
		
		List<ArticleComponent> componentList = null;
		
		//header
		componentList = article.getHeaderComponentList();
		insertComponentList(componentList,article);
		//left
		componentList = article.getLeftComponentList();
		insertComponentList(componentList,article);
		//right
		componentList = article.getRightComponentList();
		insertComponentList(componentList,article);
		//footer
		componentList = article.getFooterComponentList();
		insertComponentList(componentList,article);
		//body
		componentList = article.getBodyComponentList();
		insertComponentList(componentList,article);
	}

	private List<ArticleComponent> processComponentList(List<ArticleComponent> componentList,boolean hasExtInfo){
		if(componentList==null){
			return componentList;
		}
		
		List<ArticleComponent> componentListTarget = new ArrayList<ArticleComponent>();
		
		for(ArticleComponent component:componentList){
			componentListTarget.add(processComponent(component,hasExtInfo));
		}
		
		return componentListTarget;
	}

	private ArticleComponent processComponent(ArticleComponent articleComponent,boolean hasExtInfo) {
		if(articleComponent==null){
			return null;
		}
		ArticleComponent newArticleComponent = null;
				
		//System.out.println("=================================>"+articleComponent);
		try {
            newArticleComponent = (ArticleComponent)(Class.forName(ArticleComponent.class.getPackage().getName()+"." + articleComponent.getComponentType()).getConstructor(new Class[]{ArticleComponent.class}).newInstance(articleComponent));
        } catch (Exception e1) {
            newArticleComponent = articleComponent;
        }
        
        //System.out.println("--------------------------------->"+newArticleComponent);
        if(!hasExtInfo)return newArticleComponent;
            
		if(ArticleListComponent.class.getSimpleName().equalsIgnoreCase(newArticleComponent.getComponentType())){
			ArticleListComponent articleComponentTarget = (ArticleListComponent)(newArticleComponent);
			String content = newArticleComponent.getContent();
			
			if(StringUtils.isBlank(content)){
				return articleComponentTarget;
			}
			
			String[] articleIdStr = content.split(",");
			
			List<Article> articleList = new ArrayList<Article>();
			
			for(String idStr:articleIdStr){
				try{
					articleList.add(getObjectById(Long.parseLong(idStr)));
				}catch(Exception e){
					logger.error("",e);
				}
			}
			articleComponentTarget.setArticleList(articleList);
			
			return articleComponentTarget;
		}else if(QuickNavComponent.class.getSimpleName().equalsIgnoreCase(newArticleComponent.getComponentType())){
			QuickNavComponent articleComponentTarget = (QuickNavComponent)(newArticleComponent);
			String content = newArticleComponent.getContent();
			
			if(StringUtils.isBlank(content)){
				return articleComponentTarget;
			}
			
			String[] articleIdStr = content.split(",");
			
			List<Article> articleList = new ArrayList<Article>();
			
			for(String idStr:articleIdStr){
				try{
					articleList.add(getObjectById(Integer.parseInt(idStr)));
				}catch(Exception e){
					logger.error("",e);
				}
			}
			articleComponentTarget.setArticleList(articleList);
			
			return articleComponentTarget;
		}else if(CatalogArticleListComponent.class.getSimpleName().equalsIgnoreCase(newArticleComponent.getComponentType())){
		    CatalogArticleListComponent catalogArticleListComponent = (CatalogArticleListComponent)(newArticleComponent);
		}else if(SiteMapComponent.class.getSimpleName().equalsIgnoreCase(newArticleComponent.getComponentType())){
			SiteMapComponent siteMapComponent = (SiteMapComponent)(newArticleComponent);
			siteMapComponent.setCatalogList(catalogService.select(0, Integer.MAX_VALUE));
			
			return siteMapComponent;
		}
		
		return newArticleComponent;
	}
	
	@Override
	public ArticleComponent getArticleComponentById(long articleId,int sequence,int position){
		return articleComponentMapper.getArticleComponentById(articleId, sequence, position);
	}

	@Override
	public Article getObjectByAlias(String articleAlias, boolean hasExtInfo) {
		int articleId = articleMapper.getArticleIdByAlias(articleAlias);
		
		if(articleId == Constants.ERROR){
			return null;
		}
		return getObjectById(articleId, hasExtInfo);
	}
}
