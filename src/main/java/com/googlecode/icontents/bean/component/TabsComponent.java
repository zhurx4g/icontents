package com.googlecode.icontents.bean.component;

import org.apache.commons.beanutils.BeanUtils;

public class TabsComponent extends ArticleComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8419633019447782584L;

	public TabsComponent(){
		
	}
	public TabsComponent(ArticleComponent articleComponent){
		try {
			BeanUtils.copyProperties(this, articleComponent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
