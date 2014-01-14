package com.hyron.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hyron.db.dao.common.HibernateDao;
import com.hyron.db.domain.Client;
import com.hyron.db.domain.Tourgroup;
import com.hyron.presentation.utils.State;

public class ClientHibernateDao extends HibernateDao<Client>{
	public Client createClient(Client post){
	    return makePersistent(post);
	}
	
	public void updateClient(Client post){
		 updateRecord(post);
    }
	 
	 public void deleteClient(Client post){
		 makeTransient(post);
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Client getClientById(final int id){
		 return (Client) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM client");
					sql.append(" where id = ");
					sql.append(id);
					Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				
					Client tr = (Client) query.list().get(0);
					return tr;
				}
	    	}) ;
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Client> getClients(final State state, final String filterQuery){
		 return (List<Client>) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					List<Client> posts = new ArrayList<Client>();
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM client");
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
	public Integer getClientCount(final State state, final int userId, final String filterQuery) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuffer sql = new StringBuffer();
						sql.append("SELECT count(*) FROM client");
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
	public List<Client> getClientByCreator(final State state, final int userId, final String filterQuery){
		return (List<Client>) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<Client> posts = new ArrayList<Client>();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM client ");
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
	public Client getClientByName(final String name){
		return (Client) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM client");
				sql.append(" where name = '");
				sql.append(name);
				sql.append("'");
				Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				List l = query.list();
				Client tr = null;
			    if(l.size() > 0){
			    	tr = (Client) query.list().get(0);
			    }
				return tr;
			}
    	}) ;
	}
}
