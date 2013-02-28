package com.googlecode.icontents.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 导航设置
 * @author zhurx
 *
 */
public class Nav extends ExtendableObject implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private long id;
    private String name;
    private String link;
    private String tips;
    
    private List<Nav> navList;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getTips() {
        return tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
    }
    public List<Nav> getNavList() {
        return navList;
    }
    public void setNavList(List<Nav> navList) {
        this.navList = navList;
    }
}
