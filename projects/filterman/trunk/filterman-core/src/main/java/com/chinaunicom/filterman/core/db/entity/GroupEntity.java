package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

public class GroupEntity implements Serializable {
	private String id;
	private String groupName;
	private String type;// 0：平台组  1：应用组  2：渠道组
	private Date createDate;
	private String createUser;
	
	public GroupEntity(){
		
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
