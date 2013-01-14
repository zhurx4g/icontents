package com.googlecode.icontents.bean.component;

import java.util.List;

import com.googlecode.icontents.annotation.Extended;
import com.googlecode.icontents.bean.Article;

public class CatalogArticleListComponent extends ArticleComponent {

    /**
     *  
     */
    private static final long serialVersionUID = 5901690485965246875L;
    
    @Extended
    private long catalogId;
    
    private List<Article> articleList;
    
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

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
