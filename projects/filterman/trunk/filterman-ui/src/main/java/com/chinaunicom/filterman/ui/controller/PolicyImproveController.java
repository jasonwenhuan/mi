package com.chinaunicom.filterman.ui.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.comm.vo.IntervalVO;
import com.chinaunicom.filterman.comm.vo.RelatedPhoneVO;
import com.chinaunicom.filterman.ui.wsclient.WebServiceClient;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class PolicyImproveController {
	@Autowired
    private WebServiceClient webServiceClient;
	
	@RequestMapping(value = "relatedPhone", method = RequestMethod.GET)
    public ModelMap getRelatedPhone() {
		ModelMap mp = new ModelMap();
		return mp;
	}
	
	@RequestMapping(value = "interval", method = RequestMethod.GET)
    public ModelMap getInterval() {
		ModelMap mp = new ModelMap();
		return mp;
	}
	
	@RequestMapping(value = "getAllRelatedPhonePolicy", method = RequestMethod.GET)
    public @ResponseBody String getAllRelatedPhonePolicy() {
		List<RelatedPhoneVO> rps = webServiceClient.getAllRelatedPhonePolicy();
		
		Gson gson = new Gson();
		return gson.toJson(rps);
	}
	
	@RequestMapping(value = "getRulesByInterval", method = RequestMethod.POST)
    public @ResponseBody String getRulesByInterval(
		@RequestParam(value = "interval", defaultValue = "") String interval	
    ) {
		List<RelatedPhoneVO> rps = webServiceClient.getRulesByInterval(interval);
		
		Gson gson = new Gson();
		return gson.toJson(rps);
	}
	
	@RequestMapping(value = "updateRelatedPhoneRule", method = RequestMethod.POST)
    public @ResponseBody String updateRelatedPhoneRule(
    		@RequestParam(value = "id", defaultValue = "") String ruleId,
		@RequestParam(value = "interval", defaultValue = "") String interval,
		@RequestParam(value = "limit", defaultValue = "") String limit
    ) {
		Boolean result = webServiceClient.updateRelatedPhoneRule(ruleId, interval, limit);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "createPhoneRule", method = RequestMethod.POST)
    public @ResponseBody String createPhoneRule(
		@RequestParam(value = "interval", defaultValue = "") String interval,
		@RequestParam(value = "limit", defaultValue = "") String limit
    ) {
		Boolean result = webServiceClient.createPhoneRule(interval, limit);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "deleteRelatedPhoneRules", method = RequestMethod.POST)
    public @ResponseBody String deleteRelatedPhoneRules(
		@RequestParam(value = "ids", defaultValue = "") String[] ids
    ) {
		List<String> relatedPhoneRules = Arrays.asList(ids);
		Boolean result = webServiceClient.deleteRelatedPhoneRules(relatedPhoneRules);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "getAllIntervalPolicy", method = RequestMethod.GET)
    public @ResponseBody String getAllIntervalPolicy() {
		List<IntervalVO> intervals = webServiceClient.getAllIntervalPolicy();
		
		Gson gson = new Gson();
		return gson.toJson(intervals);
	}
	
	@RequestMapping(value = "getIntervalPoliciesByInterval", method = RequestMethod.POST)
    public @ResponseBody String getIntervalPoliciesByInterval(
		@RequestParam(value = "interval", defaultValue = "") String interval	
    ) {
		List<IntervalVO> intervals = webServiceClient.getIntervalPoliciesByInterval(interval);
		
		Gson gson = new Gson();
		return gson.toJson(intervals);
	}
	
	@RequestMapping(value = "createIntervalRule", method = RequestMethod.POST)
    public @ResponseBody String createIntervalRule(
		@RequestParam(value = "interval", defaultValue = "") String interval,
		@RequestParam(value = "limit", defaultValue = "") String limit
		
    ) {
		boolean result = webServiceClient.createIntervalRule(interval, limit);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "updateIntervalRule", method = RequestMethod.POST)
    public @ResponseBody String updateIntervalRule(
    		@RequestParam(value = "id", defaultValue = "") String ruleId,
		@RequestParam(value = "interval", defaultValue = "") String interval,
		@RequestParam(value = "limit", defaultValue = "") String limit
    ) {
		Boolean result = webServiceClient.updateIntervalRule(ruleId, interval, limit);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "deleteIntervalRules", method = RequestMethod.POST)
    public @ResponseBody String deleteIntervalRules(
		@RequestParam(value = "ids", defaultValue = "") String[] ids
    ) {
		List<String> relatedPhoneRules = Arrays.asList(ids);
		Boolean result = webServiceClient.deleteIntervalRules(relatedPhoneRules);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
