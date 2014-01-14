package com.hyron.presentation;


import java.util.ArrayList;
import java.util.List;

import com.hyron.db.domain.News;
import com.hyron.exception.BusinessException;
import com.hyron.facade.NewsFacade;
import com.hyron.javabean.NewsBean;
import com.hyron.utils.BeanBuild;

public class NewsPresManager {
	private NewsFacade newsFacade;

	public NewsFacade getNewsFacade() {
		return newsFacade;
	}

	public void setNewsFacade(NewsFacade newsFacade) {
		this.newsFacade = newsFacade;
	}

	public List<NewsBean> displayTopNewsPresManager() throws BusinessException{
		List<NewsBean> newsBean = new ArrayList<NewsBean>();
		try{
			List<News> news= newsFacade.displayTopNewsFacade();
			if(null == news){
				throw new BusinessException("Error");
			}
			newsBean = BeanBuild.buildNewsBean(news);
		}catch(BusinessException be){
			return null;
		}
		return newsBean;
	}
}
