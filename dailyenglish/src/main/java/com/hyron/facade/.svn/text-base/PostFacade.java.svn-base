package com.hyron.facade;

import java.util.List;

import com.hyron.db.dao.PostHibernateDao;
import com.hyron.db.domain.Post;
import com.hyron.db.domain.User;
import com.hyron.javabean.PostBean;
import com.hyron.presentation.utils.State;

public class PostFacade {
	private PostHibernateDao postHibernateDao;
    public List<Post> getAllPost(State state){
    	return postHibernateDao.getAllPost(state);
    }
	public PostHibernateDao getPostHibernateDao() {
		return postHibernateDao;
	}
	public void setPostHibernateDao(PostHibernateDao postHibernateDao) {
		this.postHibernateDao = postHibernateDao;
	}
	public Post getPostByTitle(String title) {
		return postHibernateDao.getPostByTitle(title);
	}
	public Post createPost(Post post) {
		return postHibernateDao.createPost(post);
	}
	public void deletePostsByTitles(String[] titles) {
		postHibernateDao.deletePostsByTitles(titles);
	}
	public Post getPostByTitleAndUser(String title,User user) {
		return postHibernateDao.getPostByTitleAndUser(title,user);
	}
	public Post updatePost(Post post) {
		return postHibernateDao.updatePost(post);
	}
	public int getPostCount() {
		return postHibernateDao.getPostCount();
	}
}
