package com.chinaunicom.filterman.comm.vo;

import java.io.Serializable;

public class SearchVO implements Serializable {
    public String key;
    public String accounts;
    public String phones;
    public String devices;
    public String appId;
    public String uid;
    public String hasBlackListData;
    
    public SearchVO(){}

    public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAccounts() {
		return accounts;
	}
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getHasBlackListData() {
		return hasBlackListData;
	}
	public void setHasBlackListData(String hasBlackListData) {
		this.hasBlackListData = hasBlackListData;
	}
}
