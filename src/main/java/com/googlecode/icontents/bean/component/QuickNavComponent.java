package com.googlecode.icontents.bean.component;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.googlecode.icontents.bean.Article;

public class QuickNavComponent extends ArticleComponent {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2938012047329872166L;
	
	private List<Article> articleList;
	
	public QuickNavComponent(){
		
	}
	public QuickNavComponent(ArticleComponent articleComponent){
		try {
			BeanUtils.copyProperties(this, articleComponent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Article> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
}
