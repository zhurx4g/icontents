package com.googlecode.icontents.service;

import java.util.List;

import com.googlecode.icontents.bean.Config;

public interface ConfigService {

    public int insert(Config object);
    
    public void update(Config object);
    
    public Config getConfigByKey(String key);
    
    public List<Config> getConfig();
}
