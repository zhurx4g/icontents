package com.googlecode.icontents.service;

import com.googlecode.icontents.bean.Article;
import com.googlecode.icontents.bean.component.ArticleComponent;

public interface ArticleService extends BaseService<Article> {
	/**
	 * 
	 * @param articleId
	 * @param hasExtInfo
	 * @return
	 */
	public Article getObjectById(long articleId,boolean hasExtInfo);
	
	public ArticleComponent getArticleComponentById(long articleId,int sequence,int position);

	/**
	 * 根据别名获取文章,获取不到返回null
	 * @param articleAlias
	 * @param hasExtInfo
	 * @return
	 */
	public Article getObjectByAlias(String articleAlias, boolean hasExtInfo);
}
