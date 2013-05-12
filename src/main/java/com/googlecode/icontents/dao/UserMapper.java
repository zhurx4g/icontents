package com.googlecode.icontents.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.googlecode.icontents.bean.User;


public interface UserMapper extends Mapper<User> {

	/**
	 * 通过email获取用户
	 * @param email
	 * @return
	 */
	public User getUserByEmail(@Param("email") String email);

	/**
	 * 通过用户名获取用户
	 * @param userName
	 * @return
	 */
	@Select("SELECT * FROM icontents_user WHERE USERNAME=#{userName}")
	public User getUserByUserName(@Param("userName") String userName);

	/**
	 * 
	 * @param email
	 * @return
	 */
	public List<User> searchUser(@Param("email") String email);

	/**
	 * 
	 * @param email
	 * @return
	 */
	public int searchUserCount(@Param("email") String email);

	/**
	 * 
	 * @param userName
	 * @param email
	 * @return
	 */
	public boolean isExist(@Param("userName") String userName, @Param("email") String email);
	
	/**
	 * 更新到
	 * @param id
	 * @return
	 */
	public boolean updateUserLevel(@Param("id") int id,@Param("level") int level);

	/**
	 * 更改密码
	 * @param id
	 * @param password
	 * @return
	 */
	boolean updateUserPassword(@Param("id") int id,@Param("password")  String password);
}
