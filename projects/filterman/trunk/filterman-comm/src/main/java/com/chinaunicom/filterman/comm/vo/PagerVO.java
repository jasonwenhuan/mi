package com.chinaunicom.filterman.comm.vo;

import java.io.Serializable;

public class PagerVO implements Serializable{
    private int pageNumber;
    private int pageOffset;
    private int rowsPerPage;
    private String groupId;
    private String appName;
    private String wblistId;
    private Object otherMessage;
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public Object getOtherMessage() {
		return otherMessage;
	}
	public void setOtherMessage(Object otherMessage) {
		this.otherMessage = otherMessage;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getWblistId() {
		return wblistId;
	}
	public void setWblistId(String wblistId) {
		this.wblistId = wblistId;
	}
}
