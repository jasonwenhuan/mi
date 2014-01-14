package com.chinaunicom.filterman.ws.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.comm.vo.IntervalVO;
import com.chinaunicom.filterman.comm.vo.RelatedPhoneVO;
import com.chinaunicom.filterman.core.bl.IIntervalBL;
import com.chinaunicom.filterman.core.bl.IRelatedPhoneRuleBL;
import com.chinaunicom.filterman.core.db.entity.IntervalMstEntity;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;
import com.chinaunicom.filterman.utilities.Logging;

@Controller
@RequestMapping(value = "/policyImprove")
public class PolicyImproveController {
	@Autowired
	private IRelatedPhoneRuleBL relatedPhoneRuleBL;
	
	@Autowired
	private IIntervalBL intervalBL;
	
	@RequestMapping(value = "/allRelatedPhoneRule", method = RequestMethod.GET)
    @ResponseBody
    public List<RelatedPhoneVO> getPoliciesByGroupId() {
		List<RelatedPhoneVO> relatedPhone = new ArrayList<RelatedPhoneVO>();
		try{
			List<RelatedPhoneRuleEntity> entities = relatedPhoneRuleBL.getAllRelatedPhoneRules();
			for(int i=0;i<entities.size();i++){
				RelatedPhoneRuleEntity entity = entities.get(i);
				RelatedPhoneVO relatedPhoneVO = new RelatedPhoneVO();
				relatedPhoneVO.setKey(entity.get_id());
				if(entity.getInterval() != 0){
					relatedPhoneVO.setTimeInterval(String.valueOf(entity.getInterval()));
				}
				if(entity.getFrequentLimit() != 0){
					relatedPhoneVO.setTotalNumber(String.valueOf(entity.getFrequentLimit()));
				}
				relatedPhone.add(relatedPhoneVO);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.getPoliciesByGroupId error", e);
		}
		return relatedPhone;
	}
	
	@RequestMapping(value = "/getRulesByInterval", method = RequestMethod.POST)
    @ResponseBody
    public List<RelatedPhoneVO> getRulesByInterval(@RequestBody String interval) {
		List<RelatedPhoneVO> relatedPhone = new ArrayList<RelatedPhoneVO>();
		try{
			List<RelatedPhoneRuleEntity> entities = relatedPhoneRuleBL.getRulesByInterval(interval);
			for(int i=0;i<entities.size();i++){
				RelatedPhoneRuleEntity entity = entities.get(i);
				RelatedPhoneVO relatedPhoneVO = new RelatedPhoneVO();
				relatedPhoneVO.setKey(entity.get_id());
				if(entity.getInterval() != 0){
					relatedPhoneVO.setTimeInterval(String.valueOf(entity.getInterval()));
				}
				if(entity.getFrequentLimit() != 0){
					relatedPhoneVO.setTotalNumber(String.valueOf(entity.getFrequentLimit()));
				}
				relatedPhone.add(relatedPhoneVO);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.getRulesByInterval error", e);
		}
		return relatedPhone;
	}
	
	@RequestMapping(value = "/updateRelatedPhoneRule", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateRelatedPhoneRule(@RequestBody RelatedPhoneVO ruleVO) {
		String key = ruleVO.getKey();
		int interval = Integer.parseInt(ruleVO.getTimeInterval());
		int limit = Integer.parseInt(ruleVO.getTotalNumber());
		try{
			return relatedPhoneRuleBL.updateRelatedPhoneRule(key, interval, limit);
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.updateRelatedPhoneRule error", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/createPhoneRule", method = RequestMethod.POST)
    @ResponseBody
    public Boolean createPhoneRule(@RequestBody RelatedPhoneVO ruleVO) {
		int interval = Integer.parseInt(ruleVO.getTimeInterval());
		int limit = Integer.parseInt(ruleVO.getTotalNumber());
		RelatedPhoneRuleEntity ruleEntity = new RelatedPhoneRuleEntity();
		ruleEntity.setInterval(interval);
		ruleEntity.setFrequentLimit(limit);
		try{
			return relatedPhoneRuleBL.createRelatedPhoneRule(ruleEntity);
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.createPhoneRule error", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/deleteRelatedPhoneRules", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteRelatedPhoneRules(@RequestBody List<String> ids) {
		try{
			if(ids == null){
				return false;
			}
			for(String id : ids){
				relatedPhoneRuleBL.removeInterval(id);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.deleteRelatedPhoneRules error", e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/allIntervalRules", method = RequestMethod.GET)
    @ResponseBody
    public List<IntervalVO> getAllIntervalRules() {
		List<IntervalVO> intervals = new ArrayList<IntervalVO>();
		try{
			List<IntervalMstEntity> entities = intervalBL.getAllIntervals();
			for(int i=0;i<entities.size();i++){
				IntervalMstEntity entity = entities.get(i);
				IntervalVO intervalVO = new IntervalVO();
				intervalVO.setKey(entity.getId());
				if(entity.getInterval() != 0){
					intervalVO.setTimeInterval(String.valueOf(entity.getInterval()));
				}
				if(entity.getLimitTotal() != 0){
					intervalVO.setTotalAccount(String.valueOf(entity.getLimitTotal()));
				}
				intervals.add(intervalVO);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.getAllIntervalRules error", e);
		}
		return intervals;
	}
	
	@RequestMapping(value = "/getIntervalPoliciesByInterval", method = RequestMethod.POST)
    @ResponseBody
    public List<IntervalVO> getIntervalPoliciesByInterval(@RequestBody String interval) {
		List<IntervalVO> intervals = new ArrayList<IntervalVO>();
		try{
			List<IntervalMstEntity> entities = intervalBL.getRulesByInterval(interval);
			for(int i=0;i<entities.size();i++){
				IntervalMstEntity entity = entities.get(i);
				IntervalVO intervalVO = new IntervalVO();
				intervalVO.setKey(entity.getId());
				if(entity.getInterval() != 0){
					intervalVO.setTimeInterval(String.valueOf(entity.getInterval()));
				}
				if(entity.getLimitTotal() != 0){
					intervalVO.setTotalAccount(String.valueOf(entity.getLimitTotal()));
				}
				intervals.add(intervalVO);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.getIntervalPoliciesByInterval error", e);
		}
		return intervals;
	}
	
	@RequestMapping(value = "/createIntervalRule", method = RequestMethod.POST)
    @ResponseBody
    public Boolean createIntervalRule(@RequestBody IntervalVO intervalVO) {
		int interval = Integer.parseInt(intervalVO.getTimeInterval());
		int limit = Integer.parseInt(intervalVO.getTotalAccount());
		IntervalMstEntity intervalEntity = new IntervalMstEntity();
		intervalEntity.setInterval(interval);
		intervalEntity.setLimitTotal(limit);
		try{
			return intervalBL.createInterval(intervalEntity);
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.createIntervalRule error", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/deleteIntervalRules", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteIntervalRules(@RequestBody List<String> ids) {
		try{
			if(ids == null){
				return false;
			}
			for(String id : ids){
				intervalBL.removeInterval(id);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.deleteIntervalRules error", e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/updateIntervalRule", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateIntervalRule(@RequestBody IntervalVO intervalVO) {
		String key = intervalVO.getKey();
		int interval = Integer.parseInt(intervalVO.getTimeInterval());
		int limit = Integer.parseInt(intervalVO.getTotalAccount());
		IntervalMstEntity entity = new IntervalMstEntity();
		entity.setId(key);
		entity.setInterval(interval);
		entity.setLimitTotal(limit);
		try{
			return intervalBL.updateInterval(entity);
		}catch(Exception e){
			Logging.logError("Doing PolicyImproveController.updateRelatedPhoneRule error", e);
		}
		return false;
	}
}
