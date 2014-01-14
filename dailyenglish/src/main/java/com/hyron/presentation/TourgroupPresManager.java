package com.hyron.presentation;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

import com.hyron.db.domain.Appoint;
import com.hyron.db.domain.Client;
import com.hyron.db.domain.Tourgroup;
import com.hyron.db.domain.User;
import com.hyron.facade.TourgroupFacade;
import com.hyron.facade.UserFacade;
import com.hyron.javabean.TourgroupBean;
import com.hyron.javabean.TourgroupData;
import com.hyron.presentation.utils.State;
import com.hyron.utils.BeanBuild;

public class TourgroupPresManager {
	public static final String USER_SESSION_NAME = "username";
	public static final String USER_SESSION_ROLE = "role";
    private TourgroupFacade tourgroupFacade;
    private UserFacade userFacade;
    
    public TourgroupFacade getTourgroupFacade() {
		return tourgroupFacade;
	}

	public void setTourgroupFacade(TourgroupFacade tourgroupFacade) {
		this.tourgroupFacade = tourgroupFacade;
	}

	public UserFacade getUserFacade() {
		return userFacade;
	}

	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	public String createTourgroup(TourgroupBean tb){
		
    	Tourgroup tourgroup = BeanBuild.buildTourgroup(tb);
    	
    	if(getLoginUserSessionName() == null){
    		tourgroup.setCreator(userFacade.getUserById(3));
    	}else{
    		tourgroup.setCreator(userFacade.getUserByUserName(getLoginUserSessionName()));
    	}
    	try{
    		Tourgroup tg = tourgroupFacade.createTourgroup(tourgroup);
    		if(tg != null){
    			return "success";
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public TourgroupData getTourgroups(State state){
		String byUser = state.getFilter().get("byUser");
		if(byUser != null){
			if(Boolean.parseBoolean(byUser)){
				String userName = getLoginUserSessionName();
				int userId = 0;
				if(userName != null){
					userId = userFacade.getUserByUserName(userName).getId();
				}
				return getTourgroupByUser(state, userId);
			}
		}
		String filterQuery = getFilterQuery(state);
		TourgroupData td = new TourgroupData();
		List<Tourgroup> ts = tourgroupFacade.getTourgroups(state, filterQuery);
		
		List<TourgroupBean> tbs = BeanBuild.buildTourgroupBeans(ts);
		td.setData(tbs);
		
		Integer count = tourgroupFacade.getTourgroupCount(state, 0, filterQuery);
		State reState = state;
		reState.getPage().setTotalRecords(count);
		td.setState(reState);
		return td;
	}
	
	public TourgroupData getTourgroupByUser(State state, int userId){
		TourgroupData td = new TourgroupData();
		String filterQuery = getFilterQuery(state);
		List<Tourgroup> ts = tourgroupFacade.getTourgroupByCreator(state, userId, filterQuery);
		
		List<TourgroupBean> tbs = BeanBuild.buildTourgroupBeans(ts);
		td.setData(tbs);
		
		Integer count = tourgroupFacade.getTourgroupCount(state, userId, filterQuery);
		State reState = state;
		reState.getPage().setTotalRecords(count);
		td.setState(reState);
		return td;
	}
	
	public TourgroupBean getTourgroupById(int tourgroupId){
		Tourgroup tg = tourgroupFacade.getTourgroupById(tourgroupId);
		TourgroupBean tb = BeanBuild.buildTourgroupBean(tg);
		return tb;
	}
	
	public String updateTourgroup(TourgroupBean tb){
		Tourgroup tourgroup = BeanBuild.buildTourgroup(tb);
		if(tb.getCreator() != null){
			User creator = userFacade.getUserByUserName(tb.getCreator());
			if(creator != null){
				tourgroup.setCreator(creator);
			}
		}else{
			return "fail";
		}
		tourgroupFacade.updateTourgroup(tourgroup);
		return "success";
	}
	
	public String deleteTourgroup(Integer[] ids){
		
		User loginUser = userFacade.getUserByUserName(getLoginUserSessionName());
		if(loginUser == null){
			return null;
		}
		for(int id : ids){
			Tourgroup tourgroup = tourgroupFacade.getTourgroupById(id);
			if(tourgroup.getCreator().getId() != loginUser.getId()){
				return null;
			}else{
				tourgroupFacade.deleteTourgroup(tourgroup);
			}
		}
		return "success";
	}
	
	public String getLoginUserSessionName(){
		HttpSession session = WebContextFactory.get().getSession();
		String userName = (String) session.getAttribute(USER_SESSION_NAME);
		return userName;
	}
	
	private String getFilterQuery(State state){
		if(state == null){
			return "";
		}
		String filter = "";
		filter += " where 1=1 ";
		if(state.getFilter().containsKey("tourgroupName")){
		    String tourgroupName = state.getFilter().get("tourgroupName");
		  
			if(tourgroupName != null && !tourgroupName.equals("")){
				filter += " and name LIKE N'%";
				filter += (tourgroupName.trim()).replaceAll("'", "''")
						.replaceAll("%", "^%").replaceAll("_", "^_");
				filter += "%' ESCAPE '^' ";
			}
			
		}
		if(state.getFilter().containsKey("creator")){
		    String creator = state.getFilter().get("creator");
		  
			if(creator != null && !creator.equals("")){
				User user = userFacade.getUserByUserName(creator);
				if(user != null){
					filter += " and creator = ";
					filter += user.getId();
				}
			}
			
		}
		filter += " ";
		return filter;
	}
}
