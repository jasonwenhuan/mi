package com.hyron.news.sina;

import java.util.List;

public class NewsRetrievers {
	private List<INewsRetriever> newsRetrievers;

	public void setNewsRetrievers(List<INewsRetriever> newsRetrievers) {
		this.newsRetrievers = newsRetrievers;
	}

	public List<INewsRetriever> getNewsRetrievers() {
		return newsRetrievers;
	}

}
