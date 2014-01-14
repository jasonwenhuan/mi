package com.hyron.api.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hyron.api.bean.MyResponse;
import com.hyron.api.bean.State;
import com.hyron.api.constant.ResponseMessage;
import com.hyron.exception.BusinessException;
import com.hyron.javabean.UserBean;
import com.hyron.presentation.UserLoginInPresManager;

@Path("API")
@Produces(MediaType.APPLICATION_JSON)
public class LoginService extends ServiceBase{
	private UserLoginInPresManager loginPresManager;
	@POST
	@Path("/test")
	public Response userLoginIn(final UserBean userBean,@Context HttpServletRequest request) {
		try {
			if (loginPresManager == null) {
				loginPresManager = (UserLoginInPresManager) getBean(request,
						"loginPresManager");
			}
			List<UserBean> users = new ArrayList<UserBean>();
			users = loginPresManager.userLoginIn(userBean.getNickName(),
					userBean.getPassword(),true);
			if (users.size() > 0) {
				return ok(new MyResponse(new State(ResponseMessage.Success),
						users));
			}
		} catch (BusinessException be) {
            return ok(new MyResponse(new State(ResponseMessage.LoginError),null));
		} catch (Exception e) {
            return Response.serverError().build();
		}
		return null;
	}
	public UserLoginInPresManager getLoginPresManager() {
		return loginPresManager;
	}
	public void setLoginPresManager(UserLoginInPresManager loginPresManager) {
		this.loginPresManager = loginPresManager;
	}
	
}
