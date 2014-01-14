package com.chinaunicom.filterman.comm.vo;

import java.io.Serializable;
import java.util.Date;

public class RequestVO implements Serializable{
	private String id;
    private Date date;
    private String orderId;
    private String appId;
    private String appName;
    private String account;
    private String device;
    private String phone;
    private Boolean isAccess;
    private String enabledPolicy;
    private String blockReason;
    private Long count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
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
	public Boolean getIsAccess() {
		return isAccess;
	}
	public void setIsAccess(Boolean isAccess) {
		this.isAccess = isAccess;
	}
	public String getBlockReason() {
		return blockReason;
	}
	public void setBlockReason(String blockReason) {
		this.blockReason = blockReason;
	}
	public String getEnabledPolicy() {
		return enabledPolicy;
	}
	public void setEnabledPolicy(String enabledPolicy) {
		this.enabledPolicy = enabledPolicy;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
