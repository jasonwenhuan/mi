package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class AccountEntity implements Serializable {
    private String id;
	private String key;
	private String phones;
	private String devices;
	private Date timestamp;
	private String appid;

    public AccountEntity(){}

	public AccountEntity(String key,
                         String phones,
                         String devices,
                         Date timestamp,
                         String appid) {
		this.key = key;
		this.phones = phones;
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
