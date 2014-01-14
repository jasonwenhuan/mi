package com.hyron.presentation;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

import com.hyron.db.domain.Appoint;
import com.hyron.db.domain.Client;
import com.hyron.db.domain.Tourgroup;
import com.hyron.db.domain.User;
import com.hyron.facade.AppointFacade;
import com.hyron.facade.ClientFacade;
import com.hyron.facade.TourgroupFacade;
import com.hyron.facade.UserFacade;
import com.hyron.javabean.AppointBean;
import com.hyron.javabean.AppointData;
import com.hyron.presentation.utils.State;
import com.hyron.utils.BeanBuild;

public class AppointPresManager {
	public static final String USER_SESSION_NAME = "username";
	public static final String USER_SESSION_ROLE = "role";
    private AppointFacade appointFacade;
    private UserFacade userFacade;
    private TourgroupFacade tourgroupFacade;
    private ClientFacade clientFacade;
    
    public AppointFacade getAppointFacade() {
		return appointFacade;
	}

	public void setAppointFacade(AppointFacade appointFacade) {
		this.appointFacade = appointFacade;
	}

	public UserFacade getUserFacade() {
		return userFacade;
	}

	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}	

	public TourgroupFacade getTourgroupFacade() {
		return tourgroupFacade;
	}

	public void setTourgroupFacade(TourgroupFacade tourgroupFacade) {
		this.tourgroupFacade = tourgroupFacade;
	}

	public ClientFacade getClientFacade() {
		return clientFacade;
	}

	public void setClientFacade(ClientFacade clientFacade) {
		this.clientFacade = clientFacade;
	}

	public String createAppoint(AppointBean tb){
    	Appoint appoint = BeanBuild.buildAppoint(tb);
    	if(getLoginUserSessionName() == null){
    		appoint.setCreator(userFacade.getUserById(3));
    	}else{
    		appoint.setCreator(userFacade.getUserByUserName(getLoginUserSessionName()));
    	}
    	appoint.setTourgroup(tourgroupFacade.getTourgroupByName(tb.getTourgroup()));
    	appoint.setClient(clientFacade.getClientByName(tb.getClient()));
    	try{
    		Appoint ap = appointFacade.createAppoint(appoint);
    		if(ap != null){
    			return "success";
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public AppointData getAppoints(State state){
		String byUser = state.getFilter().get("byUser");
		if(byUser != null){
			if(Boolean.parseBoolean(byUser)){
				String userName = getLoginUserSessionName();
				int userId = userFacade.getUserByUserName(userName).getId();
				return getAppointByUser(state, userId);
			}
		}
		
		AppointData td = new AppointData();
		
		String filterQuery = getFilterQuery(state);
		
		List<Appoint> ts = appointFacade.getAppoints(state, filterQuery);
		
		List<AppointBean> tbs = BeanBuild.buildAppointBeans(ts);
		td.setData(tbs);
		
		Integer count = appointFacade.getAppointCount(state, 0, filterQuery);
		State reState = state;
		reState.getPage().setTotalRecords(count);
		td.setState(reState);
		return td;
	}
	
	public AppointData getAppointByUser(State state, int userId){
		AppointData td = new AppointData();
		List<Appoint> ts = appointFacade.getAppointByCreator(state, userId);
		
		List<AppointBean> tbs = BeanBuild.buildAppointBeans(ts);
		td.setData(tbs);
		
		String filterQuery = getFilterQuery(state);
		
		Integer count = appointFacade.getAppointCount(state, userId, filterQuery);
		State reState = state;
		reState.getPage().setTotalRecords(count);
		td.setState(reState);
		return td;
	}
	
	public void updateAppoint(AppointBean tb){
		Appoint appoint = BeanBuild.buildAppoint(tb);
		appoint.setCreator(userFacade.getUserByUserName(tb.getCreator()));
    	appoint.setTourgroup(tourgroupFacade.getTourgroupByName(tb.getTourgroup()));
    	appoint.setClient(clientFacade.getClientByName(tb.getClient()));
    	appoint.setModifiDate(new Date());
		appointFacade.updateAppoint(appoint);
	}
	
	public String deleteAppoint(Integer[] ids){
		User loginUser = userFacade.getUserByUserName(getLoginUserSessionName());
		if(loginUser == null){
			return null;
		}
		for(int id : ids){
			Appoint appoint = appointFacade.getAppointById(id);
			if(appoint.getCreator().getId() != loginUser.getId()){
				return null;
			}else{
				appointFacade.deleteAppoint(appoint);
			}
		}
		return "success";
	}
	
	public AppointBean getAppointById(Integer appointId){
		Appoint appoint = appointFacade.getAppointById(appointId);
		AppointBean appointBean = BeanBuild.buildAppointBean(appoint);
		return appointBean;
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
		if(state.getFilter().containsKey("appointName")){
		    String appointName = state.getFilter().get("appointName");
		  
			if(appointName != null && !appointName.equals("")){
				filter += " and name LIKE N'%";
				filter += (appointName.trim()).replaceAll("'", "''")
						.replaceAll("%", "^%").replaceAll("_", "^_");
				filter += "%' ESCAPE '^' ";
			}
			
		}
		if(state.getFilter().containsKey("tourgroup")){
		    String tourgroup = state.getFilter().get("tourgroup");
		  
			if(tourgroup != null && !tourgroup.equals("")){
				Tourgroup tg = tourgroupFacade.getTourgroupByName(tourgroup);
				if(tg != null){
					filter += " and tourgroup = ";
					filter += tg.getId();
				}
			}
			
		}
		if(state.getFilter().containsKey("client")){
		    String client = state.getFilter().get("client");
		  
			if(client != null && !client.equals("")){
				Client c = clientFacade.getClientByName(client);
				if(c != null){
					filter += " and client = ";
					filter += c.getId();
				}
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
