package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class AppEntity implements Serializable {
    private String id;
	private String appId;
	private String appName;
	private String groupId;
	private String groupName;
    private Date updateDate;
    private String updateUser;
    
    public AppEntity(){}
    
    public AppEntity(String appId, String appName, String groupId,
			String groupName, Date updateDate, String updateUser) {
		super();
		this.appId = appId;
		this.appName = appName;
		this.groupId = groupId;
		this.groupName = groupName;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
    
}
