package com.hyron.db.dao;

import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hyron.db.dao.common.HibernateDao;
import com.hyron.db.domain.News;


public class NewsHibernateDao extends HibernateDao<News>{
	
	public NewsHibernateDao() {
	
	}

	
	/*public Object validateForUserLoginIn(final String userNameorEmail, final String passWord){
		return getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
             String queryString = "{call [dbo].[getUserByNameAndPassword] (?,?)}";
            	 Query query = session.createSQLQuery(queryString).addEntity(getPersistentClass());
                 query.setParameter(0, userNameorEmail);
                 query.setParameter(1, passWord);
                 return query.list().size() > 0 ? query.list() : null;
            }
		});
	}*/
	public Object displayTopNews(){
		return getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
          String queryString = " select top 5 * from [DailyEnglish].[dbo].[News] order by publishDate";
          try{
        	  Query query = session.createSQLQuery(queryString).addEntity(getPersistentClass());
              return query.list().size() > 0 ? query.list() : null;
          }catch( Exception e){
        	  return null;
          }
         
            }
		});
	}
}
