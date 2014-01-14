package com.chinaunicom.filterman.ui.controller;

import java.util.Arrays;
import java.util.List;

import com.chinaunicom.filterman.comm.vo.WBListDataVO;
import com.chinaunicom.filterman.comm.vo.WBListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class WBListController {
	@Autowired
    private WebServiceClient webServiceClient;
	
	@RequestMapping(value = "wbList", method = RequestMethod.GET)
    public ModelMap filtermanBlacklistManager() {
		ModelMap mp = new ModelMap();
		return mp;
	}
	
	@RequestMapping(value = "createBlacklist", method = RequestMethod.POST)
    public @ResponseBody String createBlacklist(
    		@RequestParam(value = "keyId", defaultValue = "-1") String keyId,
    		@RequestParam(value = "comment", defaultValue = "-1") String comment
    ) {
		WBListVO data = new WBListVO();
		data.setKeyId(keyId);
		data.setComment(comment);
		Boolean isValidKeyId = webServiceClient.createBlackList(data);
		
		Gson gson = new Gson();
		return gson.toJson(isValidKeyId);
	}
	
	@RequestMapping(value = "createWhitelist", method = RequestMethod.POST)
    public @ResponseBody String createWhitelist(
    		@RequestParam(value = "keyId", defaultValue = "-1") String keyId,
    		@RequestParam(value = "comment", defaultValue = "-1") String comment
    ) {
		WBListVO data = new WBListVO();
		data.setKeyId(keyId);
		data.setComment(comment);
		
		Boolean isValidKeyId = webServiceClient.createWhiteList(data);
		Gson gson = new Gson();
		return gson.toJson(isValidKeyId);
	}
	
	@RequestMapping(value = "removeWhitelist", method = RequestMethod.POST)
    public @ResponseBody String removeWhitelist(
    		@RequestParam(value = "whitelistIds", defaultValue = "-1") String[] whitelistIds
    ) {
		List<String> delWhitelist = Arrays.asList(whitelistIds);
		webServiceClient.removeWhiteList(delWhitelist);
		
		Gson gson = new Gson();
		return gson.toJson("success");
	}
	
	@RequestMapping(value = "removeBlacklist", method = RequestMethod.POST)
    public @ResponseBody String removeBlacklist(
    		@RequestParam(value = "blacklistIds", defaultValue = "-1") String[] blacklistIds
    ) {
		List<String> delBlacklist = Arrays.asList(blacklistIds);
		webServiceClient.removeBlackList(delBlacklist);
		
		Gson gson = new Gson();
		return gson.toJson("success");
	}
	
	@RequestMapping(value = "getBlacklist", method = RequestMethod.POST)
    public @ResponseBody String getBlacklist(
    		@RequestParam(value = "blacklistId", defaultValue = "-1") String blacklistId,
    		@RequestParam(value = "rows", defaultValue = "10") int pageSize,
    		@RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {
		WBListDataVO wbListData = new WBListDataVO();
		int pageOffset = pageSize*(pageNumber-1);
		List<WBListVO> data = webServiceClient.getBlackList(blacklistId, pageOffset, pageSize);
		
		if(data != null && data.size() > 0){
			Long count = data.get(0).getCount();
			Integer totalRecords = Integer.parseInt(String.valueOf(count));
			int totalPage = totalRecords % pageSize == 0 ? totalRecords  
	                / pageSize : totalRecords / pageSize  + 1;
			wbListData.setRecords(String.valueOf(totalRecords));
			wbListData.setTotal(String.valueOf(totalPage));
		}
		
		wbListData.setPage(String.valueOf(pageNumber));
		wbListData.setRows(data);
		
		Gson gson = new Gson();
		return gson.toJson(wbListData);
	}
	
	@RequestMapping(value = "getWhitelist", method = RequestMethod.POST)
    public @ResponseBody String getWhitelist(
    		@RequestParam(value = "whitelistId", defaultValue = "-1") String whitelistId,
    		@RequestParam(value = "rows", defaultValue = "10") int pageSize,
    		@RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {
		WBListDataVO wbListData = new WBListDataVO();
		int pageOffset = pageSize*(pageNumber-1);
		List<WBListVO> data = webServiceClient.getWhiteList(whitelistId, pageOffset, pageSize);
		
		if(data != null && data.size() > 0){
			Long count = data.get(0).getCount();
			Integer totalRecords = Integer.parseInt(String.valueOf(count));
			int totalPage = totalRecords % pageSize == 0 ? totalRecords  
	                / pageSize : totalRecords / pageSize  + 1;
			wbListData.setRecords(String.valueOf(totalRecords));
			wbListData.setTotal(String.valueOf(totalPage));
		}
		
		wbListData.setPage(String.valueOf(pageNumber));
		wbListData.setRows(data);
		
		Gson gson = new Gson();
		return gson.toJson(wbListData);
	}
	
	@RequestMapping(value = "isInBlacklist", method = RequestMethod.POST)
    public @ResponseBody String isInBlacklist(
    		@RequestParam(value = "whitelistKeyId", defaultValue = "-1") String whitelistKeyId
    ) {
		
		Boolean isInBlackList = webServiceClient.validateIsInBlacklist(whitelistKeyId);
		
		Gson gson = new Gson();
		return gson.toJson(isInBlackList);
	}
}
