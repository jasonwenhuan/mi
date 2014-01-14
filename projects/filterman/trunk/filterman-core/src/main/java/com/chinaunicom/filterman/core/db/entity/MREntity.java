package com.chinaunicom.filterman.core.db.entity;

/**
 * User: larry
 */

public class MREntity {
    private String id;
    private SubMREntity value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SubMREntity getValue() {
		return value;
	}
	public void setValue(SubMREntity value) {
		this.value = value;
	}
}
