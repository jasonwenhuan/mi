package com.chinaunicom.filterman.core.db.entity;

import java.util.Date;

public class RelatedPhoneEntity {
	private String id;
    private String shortnumber;
    private String fullnumber;
    private Date created;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShortnumber() {
		return shortnumber;
	}
	public void setShortnumber(String shortnumber) {
		this.shortnumber = shortnumber;
	}
	public String getFullnumber() {
		return fullnumber;
	}
	public void setFullnumber(String fullnumber) {
		this.fullnumber = fullnumber;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
