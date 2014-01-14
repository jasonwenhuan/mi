package com.hyron.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hyron.db.dao.common.HibernateDao;
import com.hyron.db.domain.Post;
import com.hyron.db.domain.User;
import com.hyron.javabean.UserBean;
import com.hyron.presentation.utils.State;


public class UserHibernateDao extends HibernateDao<User>{
	
	public UserHibernateDao() {
	
	}

	public User createNewUser(User user){
		return makePersistent(user);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<User> validateForUserLoginIn(final String userNameorEmail, final String passWord){
		return getHibernateTemplate().execute(new HibernateCallback() {
            public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
             //String queryString = "{call [dbo].[getUserByNameAndPassword] (?,?)}";
            	 //Query query = session.createSQLQuery(queryString).addEntity(getPersistentClass());
                 //query.setParameter(0, userNameorEmail);
                 //query.setParameter(1, passWord);
            	 String queryString = "SELECT * FROM [User] WHERE nickname = N'" + userNameorEmail
            	 + "' AND password = N'" + passWord
            	 + "' UNION SELECT * FROM [User] WHERE email = N'" + userNameorEmail
            	 + "' AND password = N'" + passWord + "'";
            	 
            	 Query query = session.createSQLQuery(queryString).addEntity(getPersistentClass());
            	 
            	 try{
            		 List list = query.list();
                	 if(list.size() > 0){
                		 return list;
                	 }
            	 }catch(Exception e){
            		 e.printStackTrace();
            	 }
            	 return null;
            }
		});
	}
	
	public User getUserById(final int userid){
	    	return (User) getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Object user = new User();
					StringBuffer sql = new StringBuffer();
					sql.append("FROM User WHERE ID = ");
					sql.append(String.valueOf(userid));
					Query query = session.createQuery(sql.toString());
					query.setReadOnly(true);
					/*if(){
						//TODO 分页
					}*/
					user = query.uniqueResult();
					return user;
				}
	    	}) ;
	}

	public User getUserByName(String username) {
		return loadByUniqueAttribute("nickname",username);
	}
}
