/**
 * 
 */
package com.auth.dao;

import com.auth.model.Roles;
import com.auth.model.User;

/**
 * @author kunal.lawand
 *
 */
public interface RoleDao {

	
	int saveRole(Roles roles,User user);
}
