package com.chinaunicom.filterman.comm.vo;

public class ModifiedVO {
    private String account;
    private String device;
    private String phone;
    private String appId;
    private String appName;
    private String changedField;
    private String changedTable;
    
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getChangedField() {
		return changedField;
	}
	public void setChangedField(String changedField) {
		this.changedField = changedField;
	}
	public String getChangedTable() {
		return changedTable;
	}
	public void setChangedTable(String changedTable) {
		this.changedTable = changedTable;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
