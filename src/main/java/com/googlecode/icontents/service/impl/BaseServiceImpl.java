package com.googlecode.icontents.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.googlecode.icontents.annotation.AutoGennerateId;
import com.googlecode.icontents.dao.Mapper;
import com.googlecode.icontents.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T>,InitializingBean{
    Log logger = LogFactory.getLog(getClass());
    
    private Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    public void setBaseDao(Mapper<T> mapper){
        this.mapper = mapper;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        setBaseDaoHandlewired();
    }
    
    public abstract void setBaseDaoHandlewired();

    @Override
    public void removeObjectById(int id) {
        mapper.removeObjectById(id);
    }

    @Override
    public void removeObjectById(long id) {
        mapper.removeObjectById(id);
    }

    @Override
    public T getObjectById(int id) {
        return mapper.getObjectById(id);
    }
    @Override
    public T getObjectById(long id) {
        return mapper.getObjectById(id);
    }

    @Override
    public int getTotal() {
        return mapper.getTotal();
    }

    @Override
    public int insert(T obj) {
        System.out.println("-->" + obj);
        int rowNum = mapper.insert(obj);
        long id = mapper.getCurrentId();
        
        System.out.println("idddddd="+id);
        boolean isAutoGennerateKey = obj.getClass().isAnnotationPresent(AutoGennerateId.class);
        
        if(isAutoGennerateKey) {
            AutoGennerateId annotation = obj.getClass().getAnnotation(AutoGennerateId.class);
            
            try {
                BeanUtils.setProperty(obj, annotation.value(), id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            
        }
        System.out.println("===>" + obj);
        return rowNum;
    }

    @Override
    public List<T> select(int page, int size) {
        if(page<=0)page=1;
        return mapper.select((page-1)*size, size);
    }

    @Override
    public void update(T obj) {
        mapper.update(obj);
    }
}
