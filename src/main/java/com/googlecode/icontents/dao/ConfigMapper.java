package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ConfigMapper<Config> extends Mapper<Config> {
    
    public Config getConfigByKey(@Param("key") String key);
    
    public List<Config> getConfig();
}
