package com.hyron.presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

import com.hyron.common.Role;
import com.hyron.db.domain.User;
import com.hyron.exception.BusinessException;
import com.hyron.facade.UserLoginInFacade;
import com.hyron.javabean.UserBean;
import com.hyron.utils.BeanBuild;

public class UserLoginInPresManager {
	public static final String USER_SESSION_NAME = "username";
	public static final String USER_SESSION_ROLE = "role";
	public UserLoginInFacade userLoginInFacade;

	public UserLoginInFacade getUserLoginInFacade() {
		return userLoginInFacade;
	}

	public void setUserLoginInFacade(UserLoginInFacade userLoginInFacade) {
		this.userLoginInFacade = userLoginInFacade;
	}

	@SuppressWarnings("unchecked")
	public List<UserBean> userLoginIn(String userNameorEmail, String passWord,boolean isApi) throws BusinessException{
		List<UserBean> userBeans = new ArrayList<UserBean>();
		try {
			List<User> users = userLoginInFacade.userLoginIn(userNameorEmail,
					passWord);
			if(users == null || users.size()<1){
				throw new BusinessException("user does not exit or password is wrong");
			}
			userBeans = BeanBuild.buildUserBean(users);
			if (null != userBeans && !isApi) {
				 HttpSession session = WebContextFactory.get().getSession();
				 session.setAttribute(USER_SESSION_NAME, userBeans.get(0).getNickName());
				 session.setAttribute(USER_SESSION_ROLE, Role.getName(userBeans.get(0).getRole()));
			}
			return userBeans;
		} catch (BusinessException be) {
			return  null;
		}
		//return userBeans;
	}
	
	
	public String getLoginUserSessionName(){
		HttpSession session = WebContextFactory.get().getSession();
		String userName = (String) session.getAttribute(USER_SESSION_NAME);
		return userName;
	}

	public int getLoginUserSessionRole(){
		HttpSession session = WebContextFactory.get().getSession();
		String userRole = (String) session.getAttribute(USER_SESSION_ROLE);
		return Role.getRoleId(userRole);
	}
	
	public boolean userLogout(String userName){
		HttpSession session = WebContextFactory.get().getSession();
  		String username = (String) session.getAttribute(USER_SESSION_NAME);
		if(username != null){
			session.setAttribute(USER_SESSION_NAME, null);
		}
		return true;
	}
	
	public String getCurrentTime(){
		/*Date d = new Date();
		String year = String.valueOf(d.getYear());
		String month = String.valueOf(d.getMonth());
		String day = String.valueOf(d.getDay());
		return year+"."+month+"."+day;*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		return time;
	}
}
