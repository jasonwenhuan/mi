package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class PhoneEntity implements Serializable {
	private String key;
	private String accounts;
	private String devices;
	private Date timestamp;
	private String appid;
    private String id;

    public PhoneEntity() {}

	public PhoneEntity(String key,
                       String accounts,
                       String devices,
                       Date timestamp,
                       String appid) {
		this.key = key;
		this.accounts = accounts;
		this.devices = devices;
		this.timestamp = timestamp;
		this.appid = appid;
	}
	
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

	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
