package com.hyron.facade;


import java.util.List;

import com.hyron.db.dao.NewsHibernateDao;
import com.hyron.db.domain.News;

public class NewsFacade {
	private NewsHibernateDao newsDao;

	public NewsHibernateDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsHibernateDao newsDao) {
		this.newsDao = newsDao;
	}
	
	@SuppressWarnings("unchecked")
	public List<News> displayTopNewsFacade(){
		return (List<News>) newsDao.displayTopNews();
	}
	
		

}
