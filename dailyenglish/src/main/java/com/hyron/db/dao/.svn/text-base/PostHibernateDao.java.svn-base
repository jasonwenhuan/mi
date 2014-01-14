package com.hyron.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hyron.db.dao.common.HibernateDao;
import com.hyron.db.domain.Post;
import com.hyron.db.domain.User;
import com.hyron.javabean.PostBean;
import com.hyron.presentation.utils.State;

public class PostHibernateDao extends HibernateDao<Post>{
    public Post createPost(Post post){
    	return makePersistent(post);
    }
    public List<Post> getAllPost(final State state){
    	return (List<Post>) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<Post> posts = new ArrayList<Post>();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM Post");
				sql.append(getSortOrder(state));
				Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				
				int pageSize = state.getPage().getRowsPerPage();
				int recordOffSet = state.getPage().getRecordOffset();
				if (pageSize > 0 && recordOffSet > -1) {
					query.setFirstResult(recordOffSet);
					query.setMaxResults(pageSize);
				}
				posts = query.list();
				return posts;
			}
    	}) ;
    }
    
    public Post getPostByTitle(String title) {
    	return loadByUniqueAttribute("title",title);
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
	public void deletePostsByTitles(String[] titles) {
		final String filterString = stringToMultiValueFilter(titles);
		if(filterString != null && filterString.length() != 0){
			getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	                String queryString = "DELETE FROM POST WHERE TITLE IN ("+filterString+")";
	                Query query = session.createSQLQuery(queryString);
	                query.executeUpdate();
	                return null;
	            }
	        });
		}
	}
	
	public Post getPostByTitleAndUser(final String title, final User user) {
		return (Post) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Post post = new Post();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM Post \n");
				sql.append("WHERE title = :title \n");
				sql.append("AND userid = :userid \n");
				Query query = session.createSQLQuery(sql.toString()).addEntity(getPersistentClass());
				query.setReadOnly(true);
				query.setParameter("title", title);
				query.setParameter("userid", user.getId());
				post = (Post) query.uniqueResult();
				return post;
			}
    	}) ;
	}
	public Post updatePost(Post post) {
		try{
			updateRecord(post);
			return post;
		}catch(Exception e){
			return null;
		}	
	}	
	
	public int getPostCount() {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT COUNT(*) FROM Post");
				Query query = session.createSQLQuery(sql.toString());
				query.setReadOnly(true);
				int count  = (Integer) query.list().get(0);
				return count;
			}
    	}) ;
	}
	
	protected String stringToMultiValueFilter(String[] titles) {
		if(titles == null || titles.length == 0){
			return null;
		}
		StringBuilder fs = new StringBuilder();
		if(titles.length>1){
			for(int i=0;i<titles.length;i++){
				fs.append("N");
				fs.append("'");
				fs.append(titles[i]);
				fs.append("'");
				if(i != titles.length-1){
					fs.append(",");
				}
			}
		}else{
			fs.append("N");
			fs.append("'");
			fs.append(titles[0]);
			fs.append("'");
		}
		return fs.toString();
	}
}
