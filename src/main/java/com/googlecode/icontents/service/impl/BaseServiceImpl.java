package com.googlecode.icontents.service.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

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
    public T insert(T obj) {
        return mapper.insert(obj);
    }

    @Override
    public List<T> select(int page, int size) {
        if(page<=0)page=1;
        size = NumberUtils.min(new int[]{100,NumberUtils.max(new int[]{10,size})});
        return mapper.select((page-1)*size, size);
    }

    @Override
    public void update(T obj) {
        mapper.update(obj);
    }
    
    public List<T> selectAll(){
    	return select(0, Integer.MAX_VALUE);
    }
}
