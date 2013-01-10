package com.googlecode.icontents.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.googlecode.icontents.bean.User;
import com.googlecode.icontents.dao.UserMapper;
import com.googlecode.icontents.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	/**
	 * 
	 */
	@Resource
	private UserMapper userMapper;
	
	@Override
	public void setBaseDaoHandlewired() {
		setBaseDao(userMapper);
	}

	@Override
	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}

	@Override
	public User getUserByUserName(String userName) {
		return userMapper.getUserByUserName(userName);
	}

	@Override
	public List<User> searchUser(String email) {
		return userMapper.searchUser(email);
	}

	@Override
	public int searchUserCount(String email) {
		return userMapper.searchUserCount(email);
	}

	@Override
	public boolean isExist(String userName, String email) {
		return userMapper.isExist(userName,email);
	}

	@Override
	public boolean updateUserLevel(int id, int level) {
		return userMapper.updateUserLevel(id, level);
	}

	@Override
	public boolean updateUserPassword(int id, String password) {
		return userMapper.updateUserPassword(id, password);
	}
}
