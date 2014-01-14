package com.hyron.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hyron.db.dao.common.HibernateDao;
import com.hyron.db.domain.Tourgroup;
import com.hyron.presentation.utils.State;

public class TourgroupHibernateDao extends HibernateDao<Tourgroup>{
	 public Tourgroup createTourgroup(Tourgroup post){
	    return makePersistent(post);
	 }
	 
	 public void updateTourgroup(Tourgroup post){
		 updateRecord(post);
     }
	 
	 public void deleteTourgroup(Tourgroup post){
		 makeTransient(post);
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Tourgroup getTourgroupById(final int id){
		 return (Tourgroup) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM tourgroup");
					sql.append(" where id = ");
					sql.append(id);
					Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				
					Tourgroup tr = (Tourgroup) query.list().get(0);
					return tr;
				}
	    	}) ;
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Tourgroup> getTourgroups(final State state, final String filterQuery){
		 return (List<Tourgroup>) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					List<Tourgroup> posts = new ArrayList<Tourgroup>();
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM tourgroup");
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
	public Integer getTourgroupCount(final State state, final int userId, final String filterQuery) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuffer sql = new StringBuffer();
						sql.append("SELECT count(*) FROM tourgroup");
						sql.append(filterQuery);
						if(userId != 0){
							sql.append(" and creator = ");
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
	public List<Tourgroup> getTourgroupByCreator(final State state, final int userId, final String filterQuery){
		return (List<Tourgroup>) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<Tourgroup> posts = new ArrayList<Tourgroup>();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM tourgroup ");
				sql.append(filterQuery);
				if(userId != 0){
					sql.append(" and creator = ");
					sql.append(userId);
				}
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
	public Tourgroup getTourgroupByName(final String name){
		 return (Tourgroup) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM tourgroup");
					sql.append(" where name = '");
					sql.append(name);
					sql.append("'");
					Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				    List l  = query.list();
				    Tourgroup tr = null;
				    if(l.size() > 0){
				    	tr = (Tourgroup) query.list().get(0);
				    }
					return tr;
				}
	    	}) ;
	}
}
