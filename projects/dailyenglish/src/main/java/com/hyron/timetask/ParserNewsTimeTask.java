package com.hyron.timetask;

import java.util.TimerTask;

import com.hyron.news.sina.INewsRetriever;
import com.hyron.news.sina.NewsRetriever;

public class ParserNewsTimeTask extends TimerTask{
	@Override
	public void run() {
		INewsRetriever inews = new NewsRetriever();
		inews.getNews();
	}
}
