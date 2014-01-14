package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class PolicyEntity implements Serializable {
	private String id;
	private String groupId;
	private String groupName;
	private String policyIds;
	private String level;
	private String status;
	private String updateUser;
	private Date updateDate;
	
	public PolicyEntity(){
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPolicyIds() {
		return policyIds;
	}

	public void setPolicyIds(String policyIds) {
		this.policyIds = policyIds;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
