package com.hyron.presentation.utils;

import java.util.HashMap;

public class State {
	private int errCode;
    private String errMessage;
    private Page page;
    private Sort sort;
    private Grouping grouping;
    private HashMap<String, String> filter;
    public State(){
    	
    }
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Grouping getGrouping() {
		return grouping;
	}
	public void setGrouping(Grouping grouping) {
		this.grouping = grouping;
	}
	public HashMap<String, String> getFilter() {
		return filter;
	}
	public void setFilter(HashMap<String, String> filter) {
		this.filter = filter;
	}
    
}
