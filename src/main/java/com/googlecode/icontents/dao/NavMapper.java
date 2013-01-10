package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.googlecode.icontents.bean.Nav;

public interface NavMapper extends Mapper<Nav>{
    
    /**
     * 
     * @param parentId
     * @return
     */
    List<Nav> selectSubNav(@Param("parentId") int parentId);

}
