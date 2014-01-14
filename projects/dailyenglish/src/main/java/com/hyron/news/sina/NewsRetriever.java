package com.hyron.news.sina;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.hyron.news.sina.NewsListData.NewsData;

public class NewsRetriever implements INewsRetriever{
	public List<NewsData> newsDataFilterBefore;
	public List<NewsData> newsDataFilterAfter;
	@Override
	public NewsListData getNews() {
		NewsListData nld = new NewsListData();
		try {
			HtmlParser hp = new HtmlParser();
			hp.initWithHandleException();
			hp.setRetryTimes(3);
			hp.setUrl("http://news.sina.com.cn/");
			newsDataFilterBefore = hp.initParser();
			
			int handleNumber = 25;
			int NO_OF_THREAD = newsDataFilterBefore.size()%handleNumber == 0 ? newsDataFilterBefore.size()/handleNumber : newsDataFilterBefore.size()/handleNumber + 1;
			//ParserRunnable pr = new ParserRunnable(newsDataFilterBefore);
			ExecutorService pool = Executors.newFixedThreadPool(5); 
			ParserRunnable pr = null;
			ArrayList<ParserRunnable> prs = new ArrayList<ParserRunnable>();
			int srcPos = 0;
			for(int i=0;i<NO_OF_THREAD;i++){
				srcPos = i*handleNumber;
				NewsData[] dest = new NewsData[handleNumber]; 
				if(i == (NO_OF_THREAD-1)){
					handleNumber = newsDataFilterBefore.size() - i*handleNumber;
				}
				System.arraycopy(newsDataFilterBefore.toArray(), srcPos, dest, 0, handleNumber);
				pr = new ParserRunnable(Arrays.asList(dest));
				prs.add(pr);
				Thread t = new Thread(pr);
				pool.execute(t);
			}
			pool.shutdown();
			//Thread t = new Thread(pr);
			//t.start();
			boolean allThreadCompleted = false;
	        long maxWaitTime = 60000;
	        long totalwaitTime = 0;
	        while (!allThreadCompleted && totalwaitTime < maxWaitTime) {
	        	boolean allCompleted = true;
	        	for(ParserRunnable p : prs){
	        		if(p.getStatus() != 3 && p.getException() == null && p.getStatus() != 2){
	        			allCompleted = false;
	        			break;
		        	}
	        	}
	            if(allCompleted){
	            	allThreadCompleted = true;
	            	break;
	            }
	            try {
	                Thread.sleep(1000);
	                totalwaitTime += 1000;
	            } catch (InterruptedException e) {
	                return null;
	            }
	        }
	        
	        if (allThreadCompleted) {
	        	if(newsDataFilterAfter == null){
	        		newsDataFilterAfter = new ArrayList<NewsData>();
	        	}
	        	for (ParserRunnable p : prs) {
	                if (p.getStatus() == ParserRunnable.STATUS_COMPLETE) {
	                	List<NewsData> currResults = p.getA();
	                	newsDataFilterAfter.addAll(currResults);
	                }
	            }
	        	/*for(int k=0;k<newsDataFilterAfter.size();k++){
	        		System.out.println(newsDataFilterAfter.get(k).getTitle() + k);
	        	}*/
	        } else {
	            return null;
	        }
			nld.setNews(newsDataFilterAfter);
			nld.setType("image");
		} catch (UnreachablePageException e) {
			e.printStackTrace();
		}
		return nld;
	}
	
	class ParserRunnable implements Runnable{
		final static int STATUS_CREATED = 0;
        final static int STATUS_PASS = 1;
        final static int STATUS_ERROR = 2; 
        final static int STATUS_COMPLETE = 3;
        private int status;
        private Exception exception;
        public List<NewsData> b;
    	public List<NewsData> a = new ArrayList<NewsData>();
    	public ParserRunnable(List<NewsData> newsDataBefore){
    		b = newsDataBefore;
    	}
    	
		public void run() {
			try{
				for (int i = 0; i < b.size(); i++) {
					String[] textAndImage = new String[3];
					textAndImage = HtmlParser.getLinkTextAndImage(b.get(i).getUrl());
					if (textAndImage[1] != null && textAndImage[1] != "") {
						b.get(i).setImagePath(textAndImage[0]);
						b.get(i).setShortDescription(textAndImage[1]);
						synchronized (this) { 
							a.add(b.get(i));
						}
						System.out.println(b.get(i).getTitle());
					}
					Thread.sleep(100);
					//System.out.println(Thread.currentThread().getName());
					status = STATUS_PASS;	
			    }	
				status = STATUS_COMPLETE;
			}catch(Exception e){
				exception = e;
				status = STATUS_ERROR;
			}
			
		}
		public List<NewsData> getB() {
			return b;
		}
		public void setB(List<NewsData> b) {
			this.b = b;
		}
		public List<NewsData> getA() {
			return a;
		}
		public void setA(List<NewsData> a) {
			this.a = a;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public Exception getException() {
			return exception;
		}
		public void setException(Exception exception) {
			this.exception = exception;
		}
		
	}
	
	public List<NewsData> getNewsDataFilterAfter() {
		return newsDataFilterAfter;
	}

	public void setNewsDataFilterAfter(List<NewsData> newsDataFilterAfter) {
		this.newsDataFilterAfter = newsDataFilterAfter;
	}

	public List<NewsData> getNewsDataFilterBefore() {
		return newsDataFilterBefore;
	}

	public void setNewsDataFilterBefore(List<NewsData> newsDataFilterBefore) {
		this.newsDataFilterBefore = newsDataFilterBefore;
	}
}
