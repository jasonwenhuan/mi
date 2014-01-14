package com.chinaunicom.filterman.comm.vo;

import java.io.Serializable;
import java.util.Date;

public class PolicyVO implements Serializable {
	//can not use id here, the jqgrid will use id as rowid,
	//this PolicyVO will have same groupPolicyKey will cause
	//jqgrid error
	//so we use groupPolicyKey here as id
    private String groupPolicyKey;
    private String groupId;
    private String groupName;
    private String level;
    private String status;
    private String updateUser;
    private Date updateDate;
    private String groupType;
    private String policyId;
    private String policyName;

    public String getGroupPolicyKey() {
		return groupPolicyKey;
	}

	public void setGroupPolicyKey(String groupPolicyKey) {
		this.groupPolicyKey = groupPolicyKey;
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

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }
}
