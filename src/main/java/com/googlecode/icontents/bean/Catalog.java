package com.googlecode.icontents.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.googlecode.icontents.annotation.AutoGennerateId;

/**
 * 分类
 */
@AutoGennerateId
public class Catalog extends ExtendableObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6039452637435837401L;
	 
	/**
	 * 分类id
	 */
	private long id;
	
	/**
	 * 分类的父id
	 */
	private int parentId;
	
	private long templateArticleId;
	/**
	 * 分类名称
	 */
	private String name;
	
	/**
	 * 分类的顺序
	 */
	private int sequence;
	
	private int weight;
	
	private String icon; 
	private String image;
	
	private String description;
	
	private int subCatalog;
	
	private List<Catalog> subCatalogList;
	
	private String link;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSubCatalog() {
		return subCatalog;
	}
	public void setSubCatalog(int subCatalog) {
		this.subCatalog = subCatalog;
	}
	public List<Catalog> getSubCatalogList() {
		return subCatalogList;
	}
	public void setSubCatalogList(List<Catalog> subCatalogList) {
		this.subCatalogList = subCatalogList;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
    public long getTemplateArticleId() {
        return templateArticleId;
    }
    public void setTemplateArticleId(long templateArticleId) {
        this.templateArticleId = templateArticleId;
    }
}
