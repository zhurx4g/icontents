package com.googlecode.icontents.bean.component;

import com.googlecode.icontents.annotation.Extended;

public class FlashComponent extends ArticleComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Extended
	private String images;
	
	public FlashComponent(){
		
	}
	public FlashComponent(ArticleComponent articleComponent){
		super(articleComponent);
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
}
