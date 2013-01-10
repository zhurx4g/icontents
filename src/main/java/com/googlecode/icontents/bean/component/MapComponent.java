package com.googlecode.icontents.bean.component;

import com.googlecode.icontents.annotation.Extended;

public class MapComponent extends ArticleComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 3895209432930879473L;

    @Extended
    private int width;
    @Extended
    private int height;
    
    public MapComponent(){
        
    }
    public MapComponent(ArticleComponent articleComponent) {
        super(articleComponent);
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    
    public static void main(String[] args) {
        System.out.println(new MapComponent(new ArticleComponent()));
    }
}
