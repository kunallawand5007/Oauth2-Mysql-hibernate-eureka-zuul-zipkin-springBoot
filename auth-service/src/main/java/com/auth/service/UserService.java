/**
 * 
 */
package com.auth.service;

import java.util.List;

import com.auth.model.User;

/**
 * @author kunal.lawand
 *
 */
public interface UserService {

	int save(User user);
	
	List<User> list();

}
