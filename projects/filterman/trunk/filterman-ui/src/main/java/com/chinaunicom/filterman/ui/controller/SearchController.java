package com.chinaunicom.filterman.ui.controller;

import java.util.List;

import com.chinaunicom.filterman.comm.vo.KeyValueVO;
import com.chinaunicom.filterman.comm.vo.SearchVO;
import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinaunicom.filterman.comm.vo.FilterVO;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class SearchController {
	@Autowired
    private WebServiceClient webServiceClient;
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelMap searchMessage() {
		ModelMap mp = new ModelMap();
		List<KeyValueVO> appIdList = webServiceClient.getAppKeyValuePair();
		mp.put("appIdList", appIdList);
		return mp;
	}
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
    public ModelMap searchResult(FilterVO filterVO) {
		
		ModelMap mp = new ModelMap();
		if (filterVO != null){
			List<SearchVO> tableDataList = webServiceClient.getResultByFilter(filterVO);
			mp.put("inputMessage", filterVO);
			mp.put("tableDataList", tableDataList);
			
			List<KeyValueVO> appIdList = webServiceClient.getAppKeyValuePair();
			mp.put("appIdList", appIdList);
		} 
		return mp;
    }
	
	@RequestMapping(value = "updateApd", method = RequestMethod.POST)
    public @ResponseBody String updateAccountPhoneDevice(
		@RequestParam(value = "changeKeyStr", defaultValue = "") String changedRecords,
		@RequestParam(value = "appId", defaultValue = "") String appId		
    ) {
		Boolean isSuccess = false;
		if(changedRecords != null && !changedRecords.equals("")){
			isSuccess = webServiceClient.updateDetail(changedRecords);
		}
		Gson gson = new Gson();
		String result = gson.toJson(isSuccess);
		return result;
    }
}
