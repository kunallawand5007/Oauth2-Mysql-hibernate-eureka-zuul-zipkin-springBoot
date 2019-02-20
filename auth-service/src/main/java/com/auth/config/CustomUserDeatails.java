/**
 * 
 */
package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.dao.UserDao;
import com.auth.model.User;

/**
 * @author kunal.lawand
 *
 */
@Service
public class CustomUserDeatails implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User findUserByEmail = userDao.findUserByEmail(username);
		if (findUserByEmail == null) {
			throw new RuntimeException("User not found..!!!");
		}
		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(findUserByEmail.getEmail(), findUserByEmail.getPassword(), true, true, true, true, findUserByEmail.getRoleses());
		return user;
	}

}
