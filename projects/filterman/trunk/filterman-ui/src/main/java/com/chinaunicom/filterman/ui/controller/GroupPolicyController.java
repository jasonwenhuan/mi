package com.chinaunicom.filterman.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.chinaunicom.filterman.comm.vo.PolicyVO;
import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
@Controller
@RequestMapping("/")
public class GroupPolicyController {
	
	@Autowired
    private WebServiceClient webServiceClient;
	
	@RequestMapping(value = "groupPolicy", method = RequestMethod.GET)
    public ModelMap filtermanGroupPolicyManager() {
		ModelMap mp = new ModelMap();
		return mp;
	}
	
	@RequestMapping(value = "currentPolicy", method = RequestMethod.POST, 
			produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getCurrenPolicyByGroupId(
       @RequestParam(value = "groupId", defaultValue = "-1") String groupId,
       @RequestParam(value = "groupLevel", defaultValue = "-1") String groupLevel
    ) {
		List<PolicyVO> policyData = new ArrayList<PolicyVO>();
		if(!groupId.equalsIgnoreCase("-1")){
			policyData = webServiceClient.getPolicyDataByGroupId(groupId, groupLevel);
		}	
		Gson gson = new Gson();
		return gson.toJson(policyData);
	}
	
	@RequestMapping(value = "allPolicy", method = RequestMethod.POST, 
			produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getAllPolicies(
    ) {
		List<PolicyVO> policyData = webServiceClient.getAllPolicy();
		if(policyData == null){
			return null;
		}
		Gson gson = new Gson();
		return gson.toJson(policyData);
	}
	
	@RequestMapping(value = "removePolicyFromGroup", method = RequestMethod.POST)
    public @ResponseBody String removePolicyFromGroup(
		@RequestParam(value = "groupPolicyId", defaultValue = "-1") String groupPolicyId,
		@RequestParam(value = "groupId", defaultValue = "-1") String groupId,
		@RequestParam(value = "policyId", defaultValue = "-1") String policyId,
		@RequestParam(value = "policyLevel", defaultValue = "-1") String policyLevel
    ) {
		Boolean result = webServiceClient.removePolicyFromGroup(groupPolicyId, policyId, policyLevel, groupId);
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	@RequestMapping(value = "addPolicyToGroup", method = RequestMethod.POST)
    public @ResponseBody String addPolicyToGroup(
        @RequestParam(value = "groupId", defaultValue = "-1") String groupId,
        @RequestParam(value = "groupName", defaultValue = "-1") String groupName,
        @RequestParam(value = "groupLevel", defaultValue = "-1") String groupLevel,
        @RequestParam(value = "groupType", defaultValue = "-1") String groupType,
		@RequestParam(value = "policyId", defaultValue = "-1") String policyId
    ) { 
		Boolean result = false;
		if(!groupId.equals("-1") && !groupName.equals("-1") 
				&& !groupLevel.equals("-1") && !groupType.equals("-1") 
				&& !policyId.equals("-1")){
			result = webServiceClient.addPolicyToGroup(groupId, groupName, groupLevel, groupType, policyId);
		}
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "updateGroupLevel", method = RequestMethod.POST)
    public @ResponseBody String updatePolicyLevel(
		@RequestParam(value = "groupId", defaultValue = "-1") String groupId,
		@RequestParam(value = "groupLevel", defaultValue = "-1") String groupLevel
    ) {
		webServiceClient.updatePolicyLevel(groupId, groupLevel);
		Gson gson = new Gson();
		return gson.toJson("success");
	}
}
