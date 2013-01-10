package com.googlecode.icontents.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.googlecode.icontents.annotation.AutoGennerateId;
import com.googlecode.icontents.bean.component.ArticleComponent;
import com.googlecode.icontents.util.JacksonUtils;

/**
 * 普通文章页面
 * @author zhurx
 *
 */
@AutoGennerateId
public class Article extends ExtendableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7284928610838899941L;

	public static final int LAYOUT_HEADER_BODY_FOOTER = 0;
	public static final int LAYOUT_HEADER_LEFT_BODY_FOOTER = 1;
	public static final int LAYOUT_HEADER_BODY_RIGHT_FOOTER = 2;
	public static final int LAYOUT_DEFAULT = 4;
	
	private long id;
	private String name;
	/**
	 * 页面别名,个性化URL
	 */
	private String alias;
	private String title;
	private String summary;
	private int visits;
	private String icon;
	private String image;
	private int weight;
	
	private int rating;
	private int ratingCount;
	boolean published;
	
	private int catalogId;
	private String catalog;
	
	private int supportCount;
	private int opposeCount;
	
	private String metaKeywords;
	private String metaDescription;
	
	@JsonIgnore
	private List<ArticleComponent> headerComponentList = new ArrayList<ArticleComponent>();
	@JsonIgnore
	private List<ArticleComponent> leftComponentList = new ArrayList<ArticleComponent>();
	@JsonIgnore
	private List<ArticleComponent> rightComponentList = new ArrayList<ArticleComponent>();
	@JsonIgnore
	private List<ArticleComponent> footerComponentList = new ArrayList<ArticleComponent>();
	@JsonIgnore
	private List<ArticleComponent> bodyComponentList = new ArrayList<ArticleComponent>();
	
	private int layout;
	
	public List<ArticleComponent> getComponentList(){
		List<ArticleComponent> componentList = new ArrayList<ArticleComponent>();
		
		componentList.addAll(headerComponentList);
		componentList.addAll(leftComponentList);
		componentList.addAll(rightComponentList);
		componentList.addAll(footerComponentList);
		componentList.addAll(bodyComponentList);
		
		return componentList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setID(long id) {
	    this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getVisits() {
		return visits;
	}
	public void setVisits(int visits) {
		this.visits = visits;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public int getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getMetaKeywords() {
		return metaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	public int getSupportCount() {
		return supportCount;
	}
	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}
	public int getOpposeCount() {
		return opposeCount;
	}
	public int getLayout() {
		return layout;
	}
	public void setLayout(int layout) {
		this.layout = layout;
	}
	public void setOpposeCount(int opposeCount) {
		this.opposeCount = opposeCount;
	}
    public String getMetaDescription() {
        return metaDescription;
    }
    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
    	return JacksonUtils.toString(this);
    }
	public List<ArticleComponent> getHeaderComponentList() {
		return headerComponentList;
	}
	public void setHeaderComponentList(List<ArticleComponent> headerComponentList) {
		this.headerComponentList = headerComponentList;
	}
	public List<ArticleComponent> getLeftComponentList() {
		return leftComponentList;
	}
	public void setLeftComponentList(List<ArticleComponent> leftComponentList) {
		this.leftComponentList = leftComponentList;
	}
	public List<ArticleComponent> getRightComponentList() {
		return rightComponentList;
	}
	public void setRightComponentList(List<ArticleComponent> rightComponentList) {
		this.rightComponentList = rightComponentList;
	}
	public List<ArticleComponent> getFooterComponentList() {
		return footerComponentList;
	}
	public void setFooterComponentList(List<ArticleComponent> footerComponentList) {
		this.footerComponentList = footerComponentList;
	}
	public List<ArticleComponent> getBodyComponentList() {
		return bodyComponentList;
	}
	public void setBodyComponentList(List<ArticleComponent> bodyComponentList) {
		this.bodyComponentList = bodyComponentList;
	}
	
	public void addComponent(ArticleComponent articleComponent){
		if(articleComponent==null){
			System.out.println("error------null component");
			return;
		}
		articleComponent.setArticleId(getId());
		if(articleComponent.getPosition()==ArticleComponent.POSITION_HEADER){
			headerComponentList.add(articleComponent);
		}else if(articleComponent.getPosition()==ArticleComponent.POSITION_LEFT){
			leftComponentList.add(articleComponent);
		}else if(articleComponent.getPosition()==ArticleComponent.POSITION_RIGHT){
			rightComponentList.add(articleComponent);
		}else if(articleComponent.getPosition()==ArticleComponent.POSITION_FOOTER){
			footerComponentList.add(articleComponent);
		}else if(articleComponent.getPosition()==ArticleComponent.POSITION_BODY){
			bodyComponentList.add(articleComponent);
		}else{
			System.out.println("error------position component"+articleComponent);
		}
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
