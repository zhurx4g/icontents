package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.googlecode.icontents.bean.component.ArticleComponent;

public interface ArticleComponentMapper extends Mapper<ArticleComponent> {

	/**
	 * 删除文章的所有组件
	 * @param articleId
	 */
	public void removeByArticleId(@Param("articleId") long articleId);
	/**
	 * 获取特定位置的组件列表
	 * @param articleId
	 * @param position
	 * @return
	 */
	public List<ArticleComponent> selectByArticleId(@Param("articleId") long articleId,@Param("position") int position);
	
	/**
	 * 获取特定位置的组件
	 * @param articleId
	 * @param sequence
	 * @param position
	 * @return
	 */
	public ArticleComponent getArticleComponentById(@Param("articleId") long articleId, @Param("sequence") int sequence, @Param("position") int position);
}
