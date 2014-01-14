package com.hyron.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hyron.db.dao.common.HibernateDao;
import com.hyron.db.domain.Appoint;
import com.hyron.presentation.utils.State;

public class AppointHibernateDao extends HibernateDao<Appoint>{
	 public Appoint createAppoint(Appoint post){
	    return makePersistent(post);
	 }
	 
	 public void updateAppoint(Appoint post){
		 updateRecord(post);
     }
	 
	 public void deleteAppoint(Appoint post){
		 makeTransient(post);
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Appoint getAppointById(final int id){
		 return (Appoint) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM Appointment");
					sql.append(" where id = ");
					sql.append(id);
					Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				
					Appoint tr = (Appoint) query.list().get(0);
					return tr;
				}
	    	}) ;
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Appoint> getAppoints(final State state, final String filterQuery){
		 return (List<Appoint>) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					List<Appoint> posts = new ArrayList<Appoint>();
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM Appointment");
					sql.append(filterQuery);
					sql.append(getSortOrder(state));
					Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
					
					if(state != null){
						int pageSize = state.getPage().getRowsPerPage();
						int recordOffSet = state.getPage().getRecordOffset();
						if (pageSize > 0 && recordOffSet > -1) {
							query.setFirstResult(recordOffSet);
							query.setMaxResults(pageSize);
						}
					}
					
					posts = query.list();
					return posts;
				}
	    	}) ;
	 }
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer getAppointCount(final State state, final int userId, final String filterQuery) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuffer sql = new StringBuffer();
						sql.append("SELECT count(*) FROM Appointment");
						sql.append(filterQuery);
						if(userId != 0){
							sql.append(" where creator = ");
							sql.append(userId);
						}
						Query query = session.createSQLQuery(sql.toString());

						Integer c =  (Integer) query.list().get(0);

						if (c != null) {
							return c;
						}
						return null;
					}
				});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Appoint> getAppointByCreator(final State state, final int userId){
		return (List<Appoint>) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<Appoint> posts = new ArrayList<Appoint>();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM Appointment ");
				sql.append(" where creator = ");
				sql.append(userId);
				sql.append(getSortOrder(state));
				Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				
				if(state != null){
					int pageSize = state.getPage().getRowsPerPage();
					int recordOffSet = state.getPage().getRecordOffset();
					if (pageSize > 0 && recordOffSet > -1) {
						query.setFirstResult(recordOffSet);
						query.setMaxResults(pageSize);
					}
				}
				
				posts = query.list();
				return posts;
			}
    	}) ;
	}
	
}

