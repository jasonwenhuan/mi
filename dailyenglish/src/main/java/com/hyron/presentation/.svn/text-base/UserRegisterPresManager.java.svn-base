package com.hyron.presentation;

import java.util.Date;

import com.hyron.common.Role;
import com.hyron.db.domain.User;
import com.hyron.facade.UserRegisterFacade;
public class UserRegisterPresManager {
	public UserRegisterFacade userRegisterFacede;
	public UserRegisterFacade getUserRegisterFacede() {
		return userRegisterFacede;
	}
	public void setUserRegisterFacede(UserRegisterFacade userRegisterFacede) {
		this.userRegisterFacede = userRegisterFacede;
	}
	
	public Object createNewUser(String nickName, String userName, String password, String email, int sex){
			User user = new User();
			user.setNickname(nickName);
			user.setUsername(userName);
			user.setPassword(password);
			user.setRole(Role.Role_Member);
			user.setEmail(email);
			user.setSex(sex);
			user.setRegisterdate(new Date());
			try{
				return (User)getUserRegisterFacede().createNewUser(user);
			}catch(Exception e){
				return e;
			}
			
	}
}
