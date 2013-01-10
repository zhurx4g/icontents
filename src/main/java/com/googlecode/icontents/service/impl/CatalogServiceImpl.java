package com.googlecode.icontents.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.googlecode.icontents.bean.Catalog;
import com.googlecode.icontents.dao.CatalogMapper;
import com.googlecode.icontents.service.CatalogService;

@Service
public class CatalogServiceImpl extends BaseServiceImpl<Catalog> implements CatalogService {
	static Logger logger = Logger.getLogger(CatalogServiceImpl.class);
	@Resource
	private CatalogMapper catalogMapper;
	
	@Override
	public void setBaseDaoHandlewired() {
		setBaseDao(catalogMapper);
	}
	
	public List<Catalog> select(int page,int size){
		List<Catalog> catalogList = super.select(page, size);
		
		for(Catalog catalog:catalogList){
			catalog.setSubCatalogList(catalogMapper.selectSubCatalog(catalog.getId()));
		}
		return catalogList;
	}
}
