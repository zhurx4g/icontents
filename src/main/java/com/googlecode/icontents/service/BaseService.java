package com.googlecode.icontents.service;

import java.util.List;

public interface BaseService<T> {
    /**
     * 插入
     * @param obj
     */
    public int insert(T obj);
    
    /**
     * 更新
     * @param obj
     */
    public void update(T obj);
    
    /**
     * 查询
     * @param page
     * @param size
     * @return
     */
    public List<T> select(int page,int size);
    
    /**
     * 查询
     * @param page
     * @param size
     * @return
     */
//    public List<T> selectMarked(int page,int size);
    
    /**
     * 
     * @param id
     * @return
     */
    public T getObjectById(int id);
    
    public T getObjectById(long id);
    
    /**
     * 
     * @param id
     */
    public void removeObjectById(int id);
    
    public void removeObjectById(long id);
    
    /**
     * 
     * @return
     */
    public int getTotal();
    
    /**
     * 
     * @param id
     * @param mark
     */
//    public void markObjectById(long id,int mark);
//    
//    public void markObjectById(long id, int mark,boolean isUnmark);
}
