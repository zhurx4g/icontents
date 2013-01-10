package com.googlecode.icontents.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.googlecode.icontents.bean.Nav;
import com.googlecode.icontents.dao.NavMapper;
import com.googlecode.icontents.service.NavService;

@Service
public class NavServiceImpl extends BaseServiceImpl<Nav> implements NavService {
    static Log logger = LogFactory.getLog(NavServiceImpl.class);
    
    @Resource
    private NavMapper navMapper;
    
    @Override
    public void setBaseDaoHandlewired() {
        setBaseDao(navMapper);
    }

    @Override
    public List<Nav> selectSubNav(int parentId) {
        return navMapper.selectSubNav(parentId);
    }

}
