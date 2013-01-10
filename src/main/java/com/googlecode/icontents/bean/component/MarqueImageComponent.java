package com.googlecode.icontents.bean.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.googlecode.icontents.annotation.Extended;


public class MarqueImageComponent extends ArticleComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 4277124544975918055L;

    @Extended
    private String images;
    @Extended
    private String links;
    @Extended
    private String titles;
    
    public MarqueImageComponent(ArticleComponent component){
        super(component);
    }
    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public String getLinks() {
        return links;
    }
    public void setLinks(String links) {
        this.links = links;
    }
    public String getTitles() {
        return titles;
    }
    public void setTitles(String titles) {
        this.titles = titles;
    }
    
    public List<Map<String,String>> getImageList(){
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();
        if(StringUtils.isBlank(images)||StringUtils.isBlank(links)||StringUtils.isBlank(titles)){
            return result;
        }
        
        String[] imageArray = images.split(",");
        String[] linkArray = links.split(",");
        String[] titleArray = titles.split(",");
        
        int length = Math.min(imageArray.length, Math.min(linkArray.length,titleArray.length));
        
        for(int i=0;i<length;i++){
            Map<String,String> bean = new HashMap<String,String>();
            
            bean.put("image", imageArray[i]);
            bean.put("link", linkArray[i]);
            bean.put("title", titleArray[i]);
        }
        
        return result;
    }
}
