/**
 * 
 */
package com.auth.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dao.UserDao;
import com.auth.model.User;

/**
 * @author kunal.lawand
 *
 */
@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	EntityManagerFactory entityManger;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public int saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try (Session openSession = entityManger.unwrap(SessionFactory.class).openSession()) {
			System.out.println(openSession.isConnected());
			openSession.save(user);
			openSession.beginTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return user.getUserid();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		try (Session openSession = entityManger.unwrap(SessionFactory.class).openSession()) {
			userList = openSession.createCriteria(User.class).list();
			openSession.beginTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public User findUserByEmail(String username) {
		User user = null;
		try (Session openSession = entityManger.unwrap(SessionFactory.class).openSession()) {
			user = (User) openSession.createCriteria(User.class).add(Restrictions.eq("email", username)).uniqueResult();
			openSession.beginTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return user;
	}

}
