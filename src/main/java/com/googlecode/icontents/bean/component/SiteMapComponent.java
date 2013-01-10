package com.googlecode.icontents.bean.component;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.googlecode.icontents.bean.Catalog;

public class SiteMapComponent extends ArticleComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9179210498120323214L;
	
	private List<Catalog> catalogList;
	
	public SiteMapComponent(){
		
	}
	public SiteMapComponent(ArticleComponent articleComponent){
		try {
			BeanUtils.copyProperties(this, articleComponent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Catalog> getCatalogList() {
		return catalogList;
	}
	public void setCatalogList(List<Catalog> catalogList) {
		this.catalogList = catalogList;
	}
}
