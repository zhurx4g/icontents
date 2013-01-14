package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.googlecode.icontents.bean.Article;

public interface ArticleMapper extends Mapper<Article> {

	/**
	 * 根据别名获取文章ID,获取不到有效的文章ID,返回-1
	 * @param articleAlias
	 * @return
	 */
	public int getArticleIdByAlias(@Param("articleAlias") String articleAlias);

	public List<Article> selectByCatalogId(@Param("catalogId") long catalogId, @Param("offset") int offset, @Param("size") int size);
}
