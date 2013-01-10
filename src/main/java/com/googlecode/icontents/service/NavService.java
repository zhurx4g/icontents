package com.googlecode.icontents.service;

import java.util.List;

import com.googlecode.icontents.bean.Nav;

public interface NavService extends BaseService<Nav> {
    public List<Nav> selectSubNav(int parentId);
}
