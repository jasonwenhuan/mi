package com.chinaunicom.filterman.core.db.entity;

public class BadBillEntity {
	private String id;
    private String userCode;
    private String cityCode;
    private String userType;
    private String comCode;
    private String comType;
    private String comeinCode;
    private String vacType;
    private String vacCode;
    private String fee; //fen
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComType() {
		return comType;
	}
	public void setComType(String comType) {
		this.comType = comType;
	}
	public String getComeinCode() {
		return comeinCode;
	}
	public void setComeinCode(String comeinCode) {
		this.comeinCode = comeinCode;
	}
	public String getVacType() {
		return vacType;
	}
	public void setVacType(String vacType) {
		this.vacType = vacType;
	}
	public String getVacCode() {
		return vacCode;
	}
	public void setVacCode(String vacCode) {
		this.vacCode = vacCode;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
}
