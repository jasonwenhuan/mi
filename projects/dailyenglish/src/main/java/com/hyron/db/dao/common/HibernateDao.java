package com.hyron.db.dao.common;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hyron.presentation.utils.State;

public class HibernateDao<T> extends HibernateDaoSupport {

    protected static final long serialVersionUID = 1L;
    private Class<T> persistentClass;

    /*mm/dd/yy*/
    protected static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
    protected static final SimpleDateFormat fullDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // intended to be used as a GroupBy parameter
    public enum GroupBy {
        DAY, WEEK, MONTH, YEAR
    }
    @SuppressWarnings("unchecked")
    public HibernateDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	protected T makePersistent(final T entity){
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				try{
				session.saveOrUpdate(entity);
				session.flush();
				session.refresh(entity);
				return entity;
				}
				catch(Exception e){
					return e;
				}
			}
		});
		return entity;
	}
	
	protected T loadByUniqueAttribute(
            final String attributeName,
            final Object attributeValue) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            	System.out.println(attributeValue);
                String queryString = MessageFormat.format("FROM {0} o WHERE {1} = :param",
                        new Object[]{getPersistentClass().getName(), attributeName});
                Query query = session.createQuery(queryString);
                query.setParameter("param", attributeValue);
                return query.uniqueResult();
            }
        });
    }
	
	protected void makeTransient(final T entity) {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.delete(entity);
                session.flush();
                return null;
            }
        });
    }
	
	/**
     * Do a single object update
     *
     * @param obj
     */
	protected void updateRecord(final Object obj) {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.update(obj);
                session.flush();
                return null;
            }
        });
    }
	
	public String getSortOrder(State state){
    	StringBuffer sb = new StringBuffer();
    	if(state != null && state.getSort() != null){
			sb.append(" order by ");
			sb.append(state.getSort().getKey()+" ");
			sb.append(state.getSort().getDir() == 0 ? "Desc" : "ASC");
		}
    	return sb.toString();
    }
	
	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static SimpleDateFormat getFormatter() {
		return formatter;
	}

	public static SimpleDateFormat getFulldateformatter() {
		return fullDateFormatter;
	}
    
}
