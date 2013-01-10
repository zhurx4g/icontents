package com.googlecode.icontents.bean.component;

import org.apache.commons.beanutils.BeanUtils;

public class LicenseComponent extends ArticleComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1160462756748764419L;
	
	public LicenseComponent(){
		
	}
	
	public LicenseComponent(ArticleComponent articleComponent){
		try {
			BeanUtils.copyProperties(this, articleComponent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
