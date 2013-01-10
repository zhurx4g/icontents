package com.googlecode.icontents.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.googlecode.icontents.bean.Config;
import com.googlecode.icontents.dao.ConfigMapper;
import com.googlecode.icontents.service.ConfigService;

@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements ConfigService {

    static Logger logger = Logger.getLogger(ConfigServiceImpl.class);
    @Resource
    private ConfigMapper<Config> configMapper;
    
    @Override
    public void setBaseDaoHandlewired() {
        setBaseDao(configMapper);
    }

    @Override
    public Config getConfigByKey(String key) {
        return configMapper.getConfigByKey(key);
    }

    @Override
    public List<Config> getConfig() {
        return configMapper.getConfig();
    }

}
