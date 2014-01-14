package com.hyron.facade;

import com.hyron.db.dao.UserHibernateDao;
import com.hyron.db.domain.User;

public class UserFacade {
    private UserHibernateDao userHibernateDao;
     
    public User getUserByUserName(String username){
    	return userHibernateDao.getUserByName(username);
    }
    
    public User getUserById(int id){
    	return userHibernateDao.getUserById(id);
    }

	public UserHibernateDao getUserHibernateDao() {
		return userHibernateDao;
	}

	public void setUserHibernateDao(UserHibernateDao userHibernateDao) {
		this.userHibernateDao = userHibernateDao;
	}
     
}
