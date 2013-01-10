package com.googlecode.icontents.bean.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.googlecode.icontents.annotation.Extended;
import com.googlecode.icontents.util.JacksonUtils;

public class ArticleComponent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7489227749772931132L;
	
	static Logger logger = Logger.getLogger(ArticleComponent.class);
	
	public static int POSITION_HEADER = 0;
	public static int POSITION_LEFT = 1;
	public static int POSITION_RIGHT = 2;
	public static int POSITION_FOOTER = 3;
	public static int POSITION_BODY = 4;
	
	private long articleId;
	private int sequence;
	private String title;
	private String image;
	private int position;
	private String content;
	private String json;
	private String name;
	private String description;
	private String link;
	
	private String componentType;
	
	public ArticleComponent(){
		
	}
	public ArticleComponent(ArticleComponent articleComponent){
		try {
			BeanUtils.copyProperties(this, articleComponent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getJson() {
        if(this.getClass().equals(ArticleComponent.class)){
            return json;
        }
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field:fields){
            try {
                if(field.isAnnotationPresent(Extended.class)){
                    String fieldName = field.getName();
                    String methodName = "get"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                    Method method = getClass().getDeclaredMethod(methodName);
                    jsonMap.put(field.getName(),method.invoke(this));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        json = JacksonUtils.toString(jsonMap);
        return json;
    }

    @SuppressWarnings("unchecked")
    public void setJson(String json) {
        if(StringUtils.isBlank(json))
            return;
        if(this.getClass().getSimpleName().equals(ArticleComponent.class.getSimpleName())){
            this.json = json;
            return;
        }
        Map<String,Object> jsonMap = JacksonUtils.toBean(json, HashMap.class);
        Field[] fields = getClass().getDeclaredFields();
        logger.debug("jsonMap: " + JacksonUtils.toString(jsonMap));
        for(Field field:fields){
            try {
                if(field.isAnnotationPresent(Extended.class)){
                    String fieldName = field.getName();
                    String methodName = "set"+ (fieldName.substring(0,1).toUpperCase()) + fieldName.substring(1);
//                    Method method = getClass().getDeclaredMethod(methodName,field.getType());
                    invokeMethod(field, String.valueOf(jsonMap.get(field.getName())), getClass());
//                    method.invoke(this, jsonMap.get(field.getName()));
                    logger.debug("methodName: " + methodName);
                    logger.debug("field.getName(): " + field.getName());
                    logger.debug("jsonMap.get(field.getName()): " + jsonMap.get(field.getName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.debug("object: " + JacksonUtils.toString(this));
        this.json = json;
    }

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getComponentType() {
	    if(componentType==null){
	        return getClass().getSimpleName();
	    }
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	private static String upper(String field){
		if(StringUtils.isNotBlank(field)){
			return field.replaceFirst(field.substring(0, 1),field.substring(0, 1).toUpperCase())  ;
		}else{
			return "";
		}
	}
	public void parse(Map<String,String> paramMap,String prefix){
		Field[] fields = getClass().getDeclaredFields();
		
		String fieldName = null;
		String fieldValue = null;
		for(int i=0;i<fields.length;i++){
			try{
				Field field = fields[i];
				fieldName = field.getName();
				fieldName = prefix+upper(fieldName);
				fieldValue = paramMap.get(fieldName);
				System.out.println("fieldName:"+fieldName+"--->objValue:"+fieldValue);
				if("serialVersionUID".equalsIgnoreCase(fieldName))
					continue;
				
				invokeMethod(field,fieldValue,getClass());
			}catch(Exception e){
				logger.error("error",e);
			}
		}
		
		fields = getClass().getSuperclass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			try{
				Field field = fields[i];
				fieldName = field.getName();
				fieldName = prefix+upper(fieldName);
				fieldValue = paramMap.get(fieldName);
				System.out.println("fieldName:"+fieldName+"--->objValue:"+fieldValue);
				if("serialVersionUID".equalsIgnoreCase(fieldName))
					continue;
				
				invokeMethod(field,fieldValue,getClass().getSuperclass());
			}catch(Exception e){
				logger.error("error",e);
			}
		}
	}
	public void parse(Map<String,String> paramMap,String prefix,int index){
		Field[] fields = getClass().getDeclaredFields();
		
		String fieldName = null;
		String fieldValue = null;
		for(int i=0;i<fields.length;i++){
			try{
				Field field = fields[i];
				fieldName = field.getName();
				if(StringUtils.isNotBlank(prefix)){
					fieldName = prefix+upper(fieldName)+index;					
				}else{
					fieldName = prefix+(fieldName)+index;
				}
				fieldValue = paramMap.get(fieldName);
				System.out.println("fieldName:"+fieldName+"--->objValue:"+fieldValue);
				if("serialVersionUID".equalsIgnoreCase(fieldName))
					continue;
				
				invokeMethod(field,fieldValue,getClass());
			}catch(Exception e){
				logger.error("error",e);
			}
		}
		
		fields = getClass().getSuperclass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			try{
				Field field = fields[i];
				fieldName = field.getName();
				if(StringUtils.isNotBlank(prefix)){
					fieldName = prefix+upper(fieldName)+index;					
				}else{
					fieldName = prefix+(fieldName)+index;
				}
				fieldValue = paramMap.get(fieldName);
				System.out.println("fieldName:"+fieldName+"--->objValue:"+fieldValue);
				if("serialVersionUID".equalsIgnoreCase(fieldName))
					continue;
				
				invokeMethod(field,fieldValue,getClass().getSuperclass());
			}catch(Exception e){
				logger.error("error",e);
			}
		}
	}
    
    private void invokeMethod(Field field,String objValue,Class<?> clazz) throws Exception{
        if(objValue==null)
            return;
        String fieldName = field.getName();
        String methodName = "set"+ (fieldName.substring(0,1).toUpperCase()) + fieldName.substring(1);
        Method method = clazz.getDeclaredMethod(methodName,field.getType());
        if(method.getParameterTypes()[0].isPrimitive()){
            String obj = objValue;
            if(method.getParameterTypes()[0] == byte.class){
                method.invoke(this, obj==null?0:Byte.parseByte(obj));                        
            }else if(method.getParameterTypes()[0] == short.class){
                method.invoke(this, obj==null?0:Short.parseShort(obj));                        
            }else if(method.getParameterTypes()[0] == int.class){
                method.invoke(this, obj==null?0:Integer.parseInt(obj));                        
            }else if(method.getParameterTypes()[0] == long.class){
                method.invoke(this, obj==null?0:Long.parseLong(obj));                        
            }else if(method.getParameterTypes()[0] == float.class){
                method.invoke(this, obj==null?0:Float.parseFloat(obj));                        
            }else if(method.getParameterTypes()[0] == double.class){
                method.invoke(this, obj==null?0:Double.parseDouble(obj));                        
            }else{
                method.invoke(this, obj);
            }
        }else{
            if(Date.class.equals(method.getParameterTypes()[0])){
                method.invoke(this, objValue==null?new Date():DateUtils.parseDate(objValue,new String[]{"yyyy-MM-dd HH:mm:ss"}));  
            }else{
                method.invoke(this, objValue);
            }
        }
    } 
    
    public String toString(){
    	return JacksonUtils.toString(this);
    }
}
