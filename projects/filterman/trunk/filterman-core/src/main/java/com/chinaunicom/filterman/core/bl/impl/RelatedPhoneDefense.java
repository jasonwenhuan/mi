package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.db.dao.BlockphoneDao;
import com.chinaunicom.filterman.core.db.dao.RelatedPhoneDao;
import com.chinaunicom.filterman.core.db.dao.RelatedPhoneRuleDao;
import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.BlockedEntity;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneEntity;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneRuleEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

public class RelatedPhoneDefense implements IDefenseBL{
	public final static String POLICY_NAME = "连号策略";
	 
	private final static String EXP_RELATED_NUMBER = "触发了[" + POLICY_NAME + "], " +
	 		"号码段[%s]在[%s]小时内累计有[%s]等手机进行支付，超出预定手机个数[%s].";

    @Autowired
    private RelatedPhoneDao relatedPhoneDao;

    @Autowired
    private RelatedPhoneRuleDao relatedPhoneRuleDao;

    @Autowired
    private BlockphoneDao blockPhoneDao;

	@Override
	public boolean execute(RequestEntity requestEntity) throws PolicyException,
			RequestException {
		
		if(!StringUtils.hasText(requestEntity.getPhone())){
            throw new RequestException("The phone is blank.", requestEntity);
		}
		
        String shortPhone = FiltermanUtils.getPrePhone(requestEntity.getPhone());

		//add to interval history
        relatedPhoneDao.saveRelatedPhone(
                createRelatedPhoneHistory(
                        shortPhone,
                        requestEntity.getPhone(),
                        requestEntity.getTimestamp()));

        Date startDate = null;
        int totalFrequent = 0;
        List<RelatedPhoneRuleEntity> rules = relatedPhoneRuleDao.getAllRelatedPhoneRules();

		for (RelatedPhoneRuleEntity entity : rules){
			startDate = FiltermanUtils.getStartDate(requestEntity.getTimestamp(), entity.getInterval());
			totalFrequent = relatedPhoneDao.getTotalFrequent(shortPhone, startDate, requestEntity.getTimestamp());

			if(totalFrequent > entity.getFrequentLimit()){
				addShortPhoneToBlockTable(shortPhone, requestEntity.getTimestamp());

				throw new PolicyException(
                        String.format(EXP_RELATED_NUMBER,
                                shortPhone,
                                entity.getInterval(),
                                getRelatedPhone(shortPhone),
                                entity.getFrequentLimit()));
			}
		}

		return false;
	}

	private String addShortPhoneToBlockTable(String shortPhone, Date date) {

        StringBuffer phones = new StringBuffer("");

		List<RelatedPhoneEntity> relatedPhones = relatedPhoneDao.getFullPhonesByShortPhone(shortPhone);

		BlockedEntity entity = null;
        for (RelatedPhoneEntity relatedPhone : relatedPhones) {
            phones.append(relatedPhone.getFullnumber());
            phones.append(",");

            entity = blockPhoneDao.getBlockByPhone(relatedPhone.getFullnumber());
            if(entity != null){
                blockPhoneDao.updateBlockByPhone(relatedPhone.getFullnumber(), date);
            }else{
                entity = FiltermanUtils.createBlockEntity(date, relatedPhone.getFullnumber(), POLICY_NAME);
                blockPhoneDao.saveBlockPhoneEntity(entity);
            }
		}

        return phones.toString();
	}
	
    private String getRelatedPhone(String prePhone) {
        StringBuffer phones = new StringBuffer("");
        List<RelatedPhoneEntity> entities = relatedPhoneDao.getFullPhonesByShortPhone(prePhone);
        if (entities != null) {
            for (RelatedPhoneEntity entity : entities) {
                phones.append(entity.getFullnumber());
                phones.append(",");
            }
        }

        return phones.toString();
    }

	private static RelatedPhoneEntity createRelatedPhoneHistory(
			String shortPhone, 
			String phone, 
			Date date) throws RequestException {
		
		RelatedPhoneEntity entity = new RelatedPhoneEntity();
		entity.setFullnumber(phone);
		entity.setShortnumber(shortPhone);
		entity.setCreated(date);
		
        return entity;
	}

    @Override
    public String toString() {
        return POLICY_NAME;
    }

}
