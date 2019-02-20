/**
 * 
 */
package com.auth.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dao.RoleDao;
import com.auth.dao.UserDao;
import com.auth.model.Roles;
import com.auth.model.User;

/**
 * @author kunal.lawand
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	RoleDao roleDao;

	@Override
	public int save(User user) {
		 int saveUser = userDao.saveUser(user);
		 Set<Roles> roleses = user.getRoleses();
		 for (Roles roles : roleses) {
			 roleDao.saveRole(roles, user);
		}
		 return saveUser;
		 
	}

	@Override
	public List<User> list() {
		return userDao.getAllUser();
	}

}
