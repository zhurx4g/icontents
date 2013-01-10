package com.googlecode.icontents.service;

import java.util.List;

import com.googlecode.icontents.bean.User;


public interface UserService extends BaseService<User> {

	public User getUserByEmail(String email);
	
	/**
	 * 通过用户名获取用户
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<User> searchUser(String email);
	
	public int searchUserCount(String email);
	
	public boolean isExist(String userName,String email);
	
	public boolean updateUserLevel(int id,int level);
	
	/**
	 * 更改密码
	 * @param id
	 * @param password
	 * @return
	 */
	boolean updateUserPassword(int id, String password);
}
