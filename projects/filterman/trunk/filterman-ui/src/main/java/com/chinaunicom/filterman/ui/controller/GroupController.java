package com.chinaunicom.filterman.ui.controller;


import java.util.Arrays;
import java.util.List;

import com.chinaunicom.filterman.comm.vo.AppDataVO;
import com.chinaunicom.filterman.comm.vo.AppVO;
import com.chinaunicom.filterman.comm.vo.GroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class GroupController {
	@Autowired
    private WebServiceClient webServiceClient;
	
	@RequestMapping(value = "groupManager", method = RequestMethod.GET)
    public ModelMap groupManager() {
		ModelMap mp = new ModelMap();
		return mp;
	}
	
	@RequestMapping(value="groups", method = RequestMethod.GET)
	public @ResponseBody String getAppGroups(
	){
		
		List<GroupVO> groupData = webServiceClient.getAllAppGroups();
		
		Gson gson = new Gson();
		return gson.toJson(groupData);
	}
	
	@RequestMapping(value="addGroup", method = RequestMethod.POST)
	public @ResponseBody String createAppGroup(
		@RequestParam(value = "groupType", defaultValue = "1") String groupType,
		@RequestParam(value = "groupName", defaultValue = "") String groupName
	){
		
		GroupVO groupData = new GroupVO();
		groupData.setCreateUser("");
		groupData.setGroupName(groupName);
		groupData.setType(groupType);
		Boolean result = webServiceClient.addAppGroup(groupData);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value="deleteGroup", method = RequestMethod.POST)
	public @ResponseBody String deleteAppGroup(
		@RequestParam(value = "groupIds", defaultValue = "0") String[] groupIds		
	){
		List<String> groupIdList = Arrays.asList(groupIds);
		Boolean result = webServiceClient.deleteAppGroup(groupIdList);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value="allApps", method = RequestMethod.GET, 
			produces = {"application/json;charset=UTF-8"})
	public @ResponseBody String getAllApps(
		@RequestParam(value = "rows", defaultValue = "10") int pageSize,
		@RequestParam(value = "page", defaultValue = "1") int pageNumber
	){  
		int pageOffset = pageSize*(pageNumber-1);
		List<AppVO> appData = webServiceClient.getAllApps(pageOffset, pageSize);
		AppDataVO aData = new AppDataVO();
		if(appData != null && appData.size() > 0){
			Long count = appData.get(0).getCount();
			Integer totalRecords = Integer.parseInt(String.valueOf(count));
			int totalPage = totalRecords % pageSize == 0 ? totalRecords  
	                / pageSize : totalRecords / pageSize  + 1;
			aData.setRecords(String.valueOf(totalRecords));
			aData.setTotal(String.valueOf(totalPage));
		}
		
		aData.setPage(String.valueOf(pageNumber));
		aData.setRows(appData);
		
		Gson gson = new Gson();
		return gson.toJson(aData);
	}
	
	@RequestMapping(value="appsByGroup", method = RequestMethod.POST, 
			produces = {"application/json;charset=UTF-8"})
	public @ResponseBody String getApplicationsByGroupId(
		@RequestParam(value = "groupId", defaultValue = "-1") String groupId,
		@RequestParam(value = "appName", defaultValue = "-1") String appName,
		@RequestParam(value = "rows", defaultValue = "10") int pageSize,
		@RequestParam(value = "page", defaultValue = "1") int pageNumber
	){  
		int pageOffset = pageSize*(pageNumber-1);
		
		List<AppVO> appData = webServiceClient.getAppsByGroupId(pageOffset, pageSize, groupId, appName);
		AppDataVO aData = new AppDataVO();
		if(appData != null && appData.size() > 0){
			Long count = appData.get(0).getCount();
			Integer totalRecords = Integer.parseInt(String.valueOf(count));
			int totalPage = totalRecords % pageSize == 0 ? totalRecords  
	                / pageSize : totalRecords / pageSize  + 1;
			aData.setRecords(String.valueOf(totalRecords));
			aData.setTotal(String.valueOf(totalPage));
		}
		
		aData.setPage(String.valueOf(pageNumber));
		aData.setRows(appData);
		Gson gson = new Gson();
		return gson.toJson(aData);
	}
	
	@RequestMapping(value="appsUpdate", method = RequestMethod.POST)
	public @ResponseBody String updateApplications(
		@RequestBody AppVO[] appData
	){  
		List<AppVO> apps = Arrays.asList(appData);
		Boolean result = webServiceClient.updateApps(apps);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
