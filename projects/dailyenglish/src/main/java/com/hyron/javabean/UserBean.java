package com.hyron.javabean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String userName;                
	private String password;
	private int role;
	private String email;
	private int sex;
	private Date registerDate;
	
	
/*	public UserBean() {
        if (null != FacesContext.getCurrentInstance()) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (null != session) {
            	Role role =new Role();
                UserBean ub = (UserBean) session.getAttribute("userSessionBean");
                if (null != ub) {
                    System.out.println("User logged in - username: " + ub.getNickName() + " role: " + role.getName(ub.getRole()));
                    this.setUserName(ub.getUserName());
                    this.setRole(ub.getRole());
                    this.setNickName(ub.getNickName());
                    this.setEmail(ub.getEmail());
                    this.setPassword(ub.getPassword());
                    this.setRegisterDate(ub.getRegisterDate());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this);
                }
            }
        }

    }*/

	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	

}
