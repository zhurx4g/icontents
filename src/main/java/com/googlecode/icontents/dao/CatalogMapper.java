package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.googlecode.icontents.bean.Catalog;

public interface CatalogMapper extends Mapper<Catalog> {

	List<Catalog> selectSubCatalog(@Param("parentId") long parentId);

}
