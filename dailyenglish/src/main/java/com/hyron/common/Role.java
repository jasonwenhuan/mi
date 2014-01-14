package com.hyron.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "[DailyEnglish].[dbo].[Role]")
public class Role {

	public static final int role_admin_ID = 1;
	public static final int role_systemAdmin_ID = 2;
	public static final int role_member_ID = 3;
	
	public static final Role Role_Admin = new Role(role_admin_ID, "Administrator");
	public static final Role Role_SystemAdmin = new Role(role_systemAdmin_ID, "System Administrator");
	public static final Role Role_Member = new Role(role_member_ID, "Member");
	
	public Role() {
	}

	public Role(int roleId, String roleName) {
	        this.roleid = roleId;
	        this.rolename = roleName;
	    }
	
	@Id
	private int roleid;
	private String rolename;

	public static String getName(int role){
		switch(role){
		case 1: return "Administrator"; 
		case 2: return "System Administrator"; 
		case 3: return "Member"; 
		}
		return "Invalid Role ID [" + role + "]";
	}
	
	public static int getRoleId(String roleName){
		if(roleName == "Administrator"){
			return role_admin_ID;
		}else if(roleName == "System Administrator"){
			return role_systemAdmin_ID;
		}else if(roleName == "Member"){
			return role_member_ID;
		}
		return -1;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	
}
