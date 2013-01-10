package com.googlecode.icontents.bean.component;

import com.googlecode.icontents.annotation.Extended;

public class CatalogArticleListComponent extends ArticleComponent {

    /**
     *  
     */
    private static final long serialVersionUID = 5901690485965246875L;
    
    @Extended
    private long catalogId;
    
    public CatalogArticleListComponent(){}
    
    public CatalogArticleListComponent(ArticleComponent component){
        super(component);
    }

    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }
     
    public static void main(String[] args) {
        System.out.println(new CatalogArticleListComponent());
    }
}
