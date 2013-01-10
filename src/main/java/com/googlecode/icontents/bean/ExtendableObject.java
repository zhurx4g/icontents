package com.googlecode.icontents.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

public class ExtendableObject implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    static Logger logger = Logger.getLogger(ExtendableObject.class);
    
	private int createUserId;
	private String creator;
	private int updateUserId;
	private String updater;
	
	private Date createTime;
	
	private Date updateTime;
	
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	
    public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void parse(Map<String,String> paramMap){
        Field[] fields = getClass().getDeclaredFields();
        
        String fieldName = null;
        String fieldValue = null;
        for(int i=0;i<fields.length;i++){
            try{
                Field field = fields[i];
                fieldName = field.getName();
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
				fieldName = prefix+upper(fieldName)+index;
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
				fieldName = prefix+upper(fieldName)+index;
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
                method.invoke(this, StringUtils.isBlank(obj)?0:Byte.parseByte(obj));                        
            }else if(method.getParameterTypes()[0] == short.class){
                method.invoke(this, StringUtils.isBlank(obj)?0:Short.parseShort(obj));                        
            }else if(method.getParameterTypes()[0] == int.class){
                method.invoke(this, StringUtils.isBlank(obj)?0:Integer.parseInt(obj));                        
            }else if(method.getParameterTypes()[0] == long.class){
                method.invoke(this, StringUtils.isBlank(obj)?0:Long.parseLong(obj));                        
            }else if(method.getParameterTypes()[0] == float.class){
                method.invoke(this, StringUtils.isBlank(obj)?0:Float.parseFloat(obj));                        
            }else if(method.getParameterTypes()[0] == double.class){
                method.invoke(this, StringUtils.isBlank(obj)?0:Double.parseDouble(obj));                        
            }else if(method.getParameterTypes()[0] == boolean.class){
            	method.invoke(this, StringUtils.isBlank(obj)?false:Boolean.parseBoolean(obj));                        
            }else{
                method.invoke(this, obj);
            }
        }else{
            if(Date.class.equals(method.getParameterTypes()[0])){
                method.invoke(this, StringUtils.isBlank(objValue)?new Date():DateUtils.parseDate(objValue,new String[]{"yyyy-MM-dd HH:mm:ss"}));  
            }else{
                method.invoke(this, objValue);
            }
        }
    } 
}
