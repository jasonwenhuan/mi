package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.bl.IRelatedPhoneRuleBL;
import com.chinaunicom.filterman.core.db.dao.RelatedPhoneRuleDao;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;


public class RelatedPhoneRuleBL implements IRelatedPhoneRuleBL{
	@Autowired
    private RelatedPhoneRuleDao relatedPhoneRuleDao;
	
	@Override
	public boolean createRelatedPhoneRule(RelatedPhoneRuleEntity entity) {
		return relatedPhoneRuleDao.saveRelatedPhoneRule(entity);
	}
	
	@Override
	public List<RelatedPhoneRuleEntity> getAllRelatedPhoneRules() {
		return relatedPhoneRuleDao.getAllRelatedPhoneRules();
	}

	@Override
	public List<RelatedPhoneRuleEntity> getRulesByInterval(String interval) {
		return relatedPhoneRuleDao.getRulesByInterval(interval);
	}

	@Override
	public boolean updateRelatedPhoneRule(String key, int interval, int limit) {
		return relatedPhoneRuleDao.updateRelatedPhoneRule(key, interval, limit);
	}

	@Override
	public boolean removeInterval(String id) {
		return relatedPhoneRuleDao.deleteRule(id);
	}

}
