package com.chinaunicom.filterman.comm.vo;

public class DetailVO {
    private String account;
    private String device;
    private String phone;
    private String changedField;
    private String changedTable;
    
    public DetailVO(){
    	
    }
    
	public DetailVO(String account, String device, String phone,
			String changedField, String changedTable) {
		super();
		this.account = account;
		this.device = device;
		this.phone = phone;
		this.changedField = changedField;
		this.changedTable = changedTable;
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
    
}
