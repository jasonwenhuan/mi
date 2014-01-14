package com.chinaunicom.filterman.core.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * User: larry
 */
public class UserEntity implements Serializable {
	
    private String name;
    private String password;
    private Date createdt;
    private Date updatedt;
    private byte status;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getCreatedt() {
		return createdt;
	}

	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}

	public Date getUpdatedt() {
		return updatedt;
	}

	public void setUpdatedt(Date updatedt) {
		this.updatedt = updatedt;
	}

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}
