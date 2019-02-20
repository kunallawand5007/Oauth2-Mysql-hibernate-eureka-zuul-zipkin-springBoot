/**
 * 
 */
package com.auth.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dao.RoleDao;
import com.auth.model.Roles;
import com.auth.model.User;

/**
 * @author kunal.lawand
 *
 */
@Service
public class RoleDaoImpl implements RoleDao {

	@Autowired
	EntityManagerFactory entityManger;

	@Override
	public int saveRole(Roles roles, User user) {
		roles.setUser(user);
		user.getRoleses().add(roles);
		try {
			Session openSession = entityManger.unwrap(SessionFactory.class).openSession();
			openSession.save(roles);
			openSession.beginTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return roles.getRoleid();
	}

}
