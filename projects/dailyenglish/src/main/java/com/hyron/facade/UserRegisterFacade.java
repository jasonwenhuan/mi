package com.hyron.facade;


import com.hyron.db.dao.UserHibernateDao;
import com.hyron.db.domain.User;

public class UserRegisterFacade {
	private UserHibernateDao userDao;
	
		
	public UserHibernateDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserHibernateDao userDao) {
		this.userDao = userDao;
	}


	public Object createNewUser(User user) {
		return getUserDao().createNewUser(user);
	}

}
