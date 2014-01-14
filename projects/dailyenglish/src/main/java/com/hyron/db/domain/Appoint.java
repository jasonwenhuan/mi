package com.hyron.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appoint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "createdate")
    private Date createDate;
	
	@Column(name = "modifiDate")
    private Date modifiDate;
	
	@ManyToOne
	@JoinColumn(name = "tourgroup")
    private Tourgroup tourgroup;
	
	@ManyToOne
	@JoinColumn(name = "client")
    private Client client;
	
	@ManyToOne
	@JoinColumn(name = "creator")
    private User creator;
    
    @Column(name = "detail")
    private String detail;

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

	public Tourgroup getTourgroup() {
		return tourgroup;
	}

	public void setTourgroup(Tourgroup tourgroup) {
		this.tourgroup = tourgroup;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}    
}
