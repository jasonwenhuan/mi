package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;

public interface IRelatedPhoneRuleBL {
    public boolean createRelatedPhoneRule(RelatedPhoneRuleEntity entity);
    public List<RelatedPhoneRuleEntity> getAllRelatedPhoneRules();
    public List<RelatedPhoneRuleEntity> getRulesByInterval(String interval);
    public boolean updateRelatedPhoneRule(String key, int interval, int limit);
	public boolean removeInterval(String id);
}
