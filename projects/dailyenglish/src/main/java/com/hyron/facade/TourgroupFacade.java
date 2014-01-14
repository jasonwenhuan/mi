package com.hyron.facade;

import java.util.List;

import com.hyron.db.dao.TourgroupHibernateDao;
import com.hyron.db.domain.Tourgroup;
import com.hyron.presentation.utils.State;

public class TourgroupFacade {
    private TourgroupHibernateDao tourgroupHibernateDao;
    
    public Tourgroup createTourgroup(Tourgroup t){
    	return tourgroupHibernateDao.createTourgroup(t);
    }
    
    public List<Tourgroup> getTourgroups(State state, String filterQuery){
    	return tourgroupHibernateDao.getTourgroups(state, filterQuery);
    }
    
    public List<Tourgroup> getTourgroupByCreator(State state, int userId, String filterQuery){
    	return tourgroupHibernateDao.getTourgroupByCreator(state, userId, filterQuery);
    }
    
    public Integer getTourgroupCount(State state, int userId, String filterQuery){
    	return tourgroupHibernateDao.getTourgroupCount(state, userId, filterQuery);
    }
    
    public Tourgroup getTourgroupByName(String name){
    	return tourgroupHibernateDao.getTourgroupByName(name);
    }
    
    public Tourgroup getTourgroupById(int id){
    	return tourgroupHibernateDao.getTourgroupById(id);
    }
    
    public void updateTourgroup(Tourgroup post){
    	tourgroupHibernateDao.updateTourgroup(post);
    }
    
    public void deleteTourgroup(Tourgroup post){
    	tourgroupHibernateDao.deleteTourgroup(post);
    }

	public TourgroupHibernateDao getTourgroupHibernateDao() {
		return tourgroupHibernateDao;
	}

	public void setTourgroupHibernateDao(TourgroupHibernateDao tourgroupHibernateDao) {
		this.tourgroupHibernateDao = tourgroupHibernateDao;
	}
    
    
}
