package com.chinaunicom.filterman.core.bl.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.BaseTest;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;

public class RelatedPhoneRuleTest extends BaseTest{
	@Autowired
    private RelatedPhoneRuleBL relatedPhoneRuleBL = (RelatedPhoneRuleBL)ctx.getBean("relatedPhoneRuleBL");
	
	@Test
	public void testCreatePhoneRule(){
		/*RelatedPhoneRuleEntity relatedPhoneRuleEntity = new RelatedPhoneRuleEntity();
		relatedPhoneRuleEntity.setInterval(1);
		relatedPhoneRuleEntity.setFrequentLimit(2);
		
		relatedPhoneRuleBL.createRelatedPhoneRule(relatedPhoneRuleEntity);*/
		
		/*RelatedPhoneRuleEntity relatedPhoneRuleEntity1 = new RelatedPhoneRuleEntity();
		relatedPhoneRuleEntity1.setInterval(2);
		relatedPhoneRuleEntity1.setFrequentLimit(4);
		
		relatedPhoneRuleBL.createRelatedPhoneRule(relatedPhoneRuleEntity1);*/
		
		/*RelatedPhoneRuleEntity relatedPhoneRuleEntity2 = new RelatedPhoneRuleEntity();
		relatedPhoneRuleEntity2.setInterval(6);
		relatedPhoneRuleEntity2.setFrequentLimit(8);
		
		relatedPhoneRuleBL.createRelatedPhoneRule(relatedPhoneRuleEntity2);*/
		
		/*RelatedPhoneRuleEntity relatedPhoneRuleEntity3 = new RelatedPhoneRuleEntity();
		relatedPhoneRuleEntity3.setInterval(12);
		relatedPhoneRuleEntity3.setFrequentLimit(16);
		
		relatedPhoneRuleBL.createRelatedPhoneRule(relatedPhoneRuleEntity3);*/
		
		RelatedPhoneRuleEntity relatedPhoneRuleEntity4 = new RelatedPhoneRuleEntity();
		relatedPhoneRuleEntity4.setInterval(24);
		relatedPhoneRuleEntity4.setFrequentLimit(32);
		
		relatedPhoneRuleBL.createRelatedPhoneRule(relatedPhoneRuleEntity4);
	}
}
