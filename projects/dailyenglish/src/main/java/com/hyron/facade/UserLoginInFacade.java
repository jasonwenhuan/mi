package com.hyron.facade;

import java.util.List;

import com.hyron.db.dao.UserHibernateDao;
import com.hyron.db.domain.User;
public class UserLoginInFacade {
	
	UserHibernateDao userDao;
	
	public UserHibernateDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserHibernateDao userDao) {
		this.userDao = userDao;
	}

	@SuppressWarnings("unchecked")
	public List<User> userLoginIn(String userNameorEmail, String passWord){
		return  (List<User>)userDao.validateForUserLoginIn(userNameorEmail, passWord);
	}
    
}
