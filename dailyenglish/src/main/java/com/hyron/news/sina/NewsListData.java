package com.hyron.news.sina;

import java.util.ArrayList;
import java.util.List;

public class NewsListData {
	private String type;
	private List<NewsData> news;

	public static class NewsData {
		private String title;
		private String shortDescription;
		private String imagePath;
		private String url;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getShortDescription() {
			return shortDescription;
		}
		public void setShortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
		}
		public String getImagePath() {
			return imagePath;
		}
		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<NewsData> getNews() {
		if (news == null) {
			news = new ArrayList<NewsData>();
		}
		return news;
	}

	public void setNews(List<NewsData> news) {
		this.news = news;
	}
	
}
