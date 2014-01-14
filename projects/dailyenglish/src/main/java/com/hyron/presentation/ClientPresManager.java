package com.hyron.presentation;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

import com.hyron.db.domain.Client;
import com.hyron.db.domain.Tourgroup;
import com.hyron.db.domain.User;
import com.hyron.facade.ClientFacade;
import com.hyron.facade.UserFacade;
import com.hyron.javabean.ClientBean;
import com.hyron.javabean.ClientData;
import com.hyron.javabean.TourgroupBean;
import com.hyron.presentation.utils.State;
import com.hyron.utils.BeanBuild;

public class ClientPresManager {
	public static final String USER_SESSION_NAME = "username";
	public static final String USER_SESSION_ROLE = "role";
    private ClientFacade clientFacade;
    private UserFacade userFacade;
    
    public ClientFacade getclientFacade() {
		return clientFacade;
	}

	public void setclientFacade(ClientFacade clientFacade) {
		this.clientFacade = clientFacade;
	}

	public UserFacade getUserFacade() {
		return userFacade;
	}

	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	public String createClient(ClientBean tb){
    	Client client = BeanBuild.buildClient(tb);
    	if(getLoginUserSessionName() == null){
    		client.setCreator(userFacade.getUserById(3));
    	}else{
    		client.setCreator(userFacade.getUserByUserName(getLoginUserSessionName()));
    	}
    	try{
    		Client c = clientFacade.createClient(client);
    		if(c != null){
    			return "success";
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public ClientData getClients(State state){
		String byUser = state.getFilter().get("byUser");
		if(byUser != null){
			if(Boolean.parseBoolean(byUser)){
				String userName = getLoginUserSessionName();
				int userId = 0;
				if(userName != null){
					userId = userFacade.getUserByUserName(userName).getId();
				}
				return getClientByUser(state, userId);
			}
		}
		String filterQuery = getFilterQuery(state);
		ClientData td = new ClientData();
		List<Client> ts = clientFacade.getClient(state, filterQuery);
		
		List<ClientBean> tbs = BeanBuild.buildClientBeans(ts);
		td.setData(tbs);
		
		Integer count = clientFacade.getClientCount(state, 0, filterQuery);
		State reState = state;
		reState.getPage().setTotalRecords(count);
		td.setState(reState);
		return td;
	}
	
	public ClientData getClientByUser(State state, int userId){
		ClientData td = new ClientData();
		
		String filterQuery = getFilterQuery(state);
		
		List<Client> ts = clientFacade.getClientByCreator(state, userId, filterQuery);
		
		List<ClientBean> tbs = BeanBuild.buildClientBeans(ts);
		td.setData(tbs);
		
		Integer count = clientFacade.getClientCount(state, userId, filterQuery);
		State reState = state;
		reState.getPage().setTotalRecords(count);
		td.setState(reState);
		return td;
	}
	
	public ClientBean getClientById(int id){
		Client client = clientFacade.getClientById(id);
		ClientBean tb = BeanBuild.buildClientBean(client);
		return tb;
	}
	
	public String updateClient(ClientBean tb){
		Client client = BeanBuild.buildClient(tb);
		if(tb.getCreator() != null){
			User creator = userFacade.getUserByUserName(tb.getCreator());
			if(creator != null){
				client.setCreator(creator);
			}
		}else{
			return "fail";
		}
		clientFacade.updateClient(client);
		return "success";
	}
	
	public String deleteClient(Integer[] ids){
		User loginUser = userFacade.getUserByUserName(getLoginUserSessionName());
		if(loginUser == null){
			return null;
		}
		for(int id : ids){
			Client client = clientFacade.getClientById(id);
			if(client.getCreator().getId() != loginUser.getId()){
				return null;
			}else{
				clientFacade.deleteClient(client);
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
		if(state.getFilter().containsKey("clientName")){
		    String clientName = state.getFilter().get("clientName");
		  
			if(clientName != null && !clientName.equals("")){
				filter += " and name LIKE N'%";
				filter += (clientName.trim()).replaceAll("'", "''")
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
