package com.hyron.news.sina;

@SuppressWarnings("serial")
public class UnreachablePageException extends Exception {
	protected String url;
	protected String reason;

	public UnreachablePageException(String url, String reason) {
		super("Can not get page from this url " + url + "\n" + reason);
		this.reason = reason;
		this.url = url;
	}

	public String getExceptionUrl() {
		return this.url;
	}

	public String getReason() {
		return this.reason;
	}

}
