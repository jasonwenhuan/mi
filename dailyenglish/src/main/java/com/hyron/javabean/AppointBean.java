package com.hyron.javabean;

import java.util.Date;

public class AppointBean {

    private Integer id;

    private String name;
	
    private Date createDate;
	
    private Date modifiDate;
	
    private String tourgroup;
	
    private String client;
	
    private String creator;
    
    private String detail;
    
    public AppointBean(){
    	
    }

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiDate() {
		return modifiDate;
	}

	public void setModifiDate(Date modifiDate) {
		this.modifiDate = modifiDate;
	}

	public String getTourgroup() {
		return tourgroup;
	}

	public void setTourgroup(String tourgroup) {
		this.tourgroup = tourgroup;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
    
    
}
