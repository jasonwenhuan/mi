package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceEntity implements Serializable {
	private String key;
	private String accounts;
	private String phones;
	private Date timestamp;
	private String appid;
    private String id;

    public DeviceEntity(){}

	public DeviceEntity(String key,
                        String accounts,
                        String phones,
                        Date timestamp,
                        String appid) {
		this.key = key;
		this.accounts = accounts;
		this.phones = phones;
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

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
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
