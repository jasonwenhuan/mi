package com.hyron.facade;

import java.util.List;

import com.hyron.db.dao.AppointHibernateDao;
import com.hyron.db.domain.Appoint;
import com.hyron.presentation.utils.State;

public class AppointFacade {
    private AppointHibernateDao appointHibernateDao;
    
    public Appoint createAppoint(Appoint t){
    	return appointHibernateDao.createAppoint(t);
    }
    
    public List<Appoint> getAppoints(State state, String filterQuery){
    	return appointHibernateDao.getAppoints(state, filterQuery);
    }
    
    public List<Appoint> getAppointByCreator(State state, int userId){
    	return appointHibernateDao.getAppointByCreator(state, userId);
    }
    
    public Integer getAppointCount(State state, int userId, String filterQuery){
    	return appointHibernateDao.getAppointCount(state, userId, filterQuery);
    }
    
    public Appoint getAppointById(int id){
    	return appointHibernateDao.getAppointById(id);
    }
    
    public void updateAppoint(Appoint post){
    	appointHibernateDao.updateAppoint(post);
    }
    
    public void deleteAppoint(Appoint post){
    	appointHibernateDao.deleteAppoint(post);
    }

	public AppointHibernateDao getappointHibernateDao() {
		return appointHibernateDao;
	}

	public void setappointHibernateDao(AppointHibernateDao appointHibernateDao) {
		this.appointHibernateDao = appointHibernateDao;
	}
    
    
}
