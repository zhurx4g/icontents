package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.googlecode.icontents.bean.Catalog;

public interface CatalogMapper extends Mapper<Catalog> {

	@Select("SELECT "+
			"C.*,"+
			"(SELECT COUNT(1) FROM icontents_catalog WHERE PARENTID=C.ID) SUBCATALOG,"+
			"(SELECT USERNAME FROM icontents_user U  WHERE C.CREATEUSERID=U.ID) CREATOR,"+
			"(SELECT USERNAME FROM icontents_user U  WHERE C.UPDATEUSERID=U.ID) UPDATER "+
			"FROM icontents_catalog C "+
			"WHERE PARENTID=#{parentId} "+
			"ORDER BY SEQUENCE DESC")
	List<Catalog> selectSubCatalog(@Param("parentId") long parentId);
	/**
	 * 
	 * @param object
	 */
	public Catalog insert(@Param("obj") Catalog object);
	
    /**
     * 
     * @return
     */
    public long getCurrentId();
    
	/**
	 * 
	 * @param object
	 */
	public void update(@Param("obj") Catalog object);
	
	/**
	 * get object by id
	 * @param id
	 * @return
	 */
	public Catalog getObjectById(@Param("id") long id);
	/**
	 * get object by id
	 * @param id
	 * @return
	 */
	public Catalog getObjectById(@Param("id") int id);
	
	/**
	 * delete
	 * @param id
	 */
	public void removeObjectById(@Param("id") long id);
	/**
	 * delete
	 * @param id
	 */
	public void removeObjectById(@Param("id") String id);
	
	/**
	 * get section by page
	 * @param page
	 * @param size
	 * @return
	 */
	@Select("SELECT  C.*," + 
			"(SELECT COUNT(1) FROM icontents_catalog WHERE PARENTID=C.ID) SUBCATALOG," + 
			"(SELECT USERNAME FROM icontents_user U  WHERE C.CREATEUSERID=U.ID) CREATOR," + 
			"(SELECT USERNAME FROM icontents_user U  WHERE C.UPDATEUSERID=U.ID) UPDATER " + 
			"FROM icontents_catalog C " + 
			"WHERE PARENTID=0 " + 
			"ORDER BY SEQUENCE DESC " + "LIMIT #{offset},#{size}")
	public List<Catalog> select(@Param("offset") int offset, @Param("size") int size);

    public int getTotal();
}
