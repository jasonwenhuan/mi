package com.chinaunicom.filterman.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.comm.vo.ModifiedVO;
import com.chinaunicom.filterman.comm.vo.RequestDataVO;
import com.chinaunicom.filterman.comm.vo.RequestVO;
import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class GeneralController {
	@Autowired
    private WebServiceClient webServiceClient;
	
	@RequestMapping(value = "general", method = RequestMethod.GET)
    public ModelMap groupManager() {
		ModelMap mp = new ModelMap();
		return mp;
	}
	
	@RequestMapping(value = "allRequests", method = RequestMethod.POST, 
			produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getAllRequests(
        @RequestParam (value = "orderIdOrPhone", defaultValue = "-1") String orderIdOrPhone,
        @RequestParam(value = "rows", defaultValue = "10") int pageSize,
		@RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {
		RequestDataVO requestData = new RequestDataVO();
		int pageOffset = pageSize*(pageNumber-1);
		List<RequestVO> requests = webServiceClient.getAllRequests(orderIdOrPhone, pageOffset, pageSize);
		if(requests != null && requests.size() > 0){
			Long count = requests.get(0).getCount();
			Integer totalRecords = Integer.parseInt(String.valueOf(count));
			int totalPage = totalRecords % pageSize == 0 ? totalRecords  
	                / pageSize : totalRecords / pageSize  + 1;
			requestData.setRecords(String.valueOf(totalRecords));
			requestData.setTotal(String.valueOf(totalPage));
		}
		
		requestData.setPage(String.valueOf(pageNumber));
		requestData.setRows(requests);
		
		Gson gson = new Gson();
		return gson.toJson(requestData);
	}
	
	@RequestMapping(value = "fieldsUpdate", method = RequestMethod.POST)
    public @ResponseBody String update(
        @RequestParam (value = "account", defaultValue = "-1") String account,		
        @RequestParam (value = "device", defaultValue = "-1") String device,
        @RequestParam (value = "phone", defaultValue = "-1") String phone,
        @RequestParam (value = "appId", defaultValue = "-1") String appId,
        @RequestParam (value = "appName", defaultValue = "-1") String appName,
        @RequestParam (value = "changedField", defaultValue = "-1") String changedField,
        @RequestParam (value = "changedTable", defaultValue = "-1") String changedTable
    ) {  
		Boolean result = webServiceClient.updateAccountPhoneDevice(account, device, phone, appId, appName, changedField, changedTable);
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	@RequestMapping(value = "getDetailByKey", method = RequestMethod.POST)
    public @ResponseBody String getDetailByKey(
        @RequestParam (value = "account", defaultValue = "-1") String account,		
        @RequestParam (value = "device", defaultValue = "-1") String device,
        @RequestParam (value = "phone", defaultValue = "-1") String phone,
        @RequestParam (value = "appId", defaultValue = "-1") String appId,
        @RequestParam (value = "appName", defaultValue = "-1") String appName,
        @RequestParam (value = "changedField", defaultValue = "-1") String changedField,
        @RequestParam (value = "changedTable", defaultValue = "-1") String changedTable
    ) {  
		ModifiedVO result = webServiceClient.getAccountPhoneDeviceDetailByKey(account, device, phone, appId, appName, changedField, changedTable);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	
}
