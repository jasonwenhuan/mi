package com.chinaunicom.filterman.core.bl.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.bl.IRelatedPhoneBL;
import com.chinaunicom.filterman.core.db.dao.RelatedPhoneDao;
import com.chinaunicom.filterman.core.db.entity.RelatedPhoneEntity;

public class RelatedPhoneBL implements IRelatedPhoneBL{
	@Autowired
    private RelatedPhoneDao relatedPhoneDao;
	
	@Override
	public boolean createRelatedPhone(RelatedPhoneEntity entity) {
		return relatedPhoneDao.saveRelatedPhone(entity);
	}

	@Override
	public int getTotalFrequent(String shortPhone, Date startTime, Date endTime) {
		return relatedPhoneDao.getTotalFrequent(shortPhone, startTime, endTime);
	}

	@Override
	public List<RelatedPhoneEntity> getFullPhonesByShortPhone(String shortPhone) {
		return relatedPhoneDao.getFullPhonesByShortPhone(shortPhone);
	}
	
	@Override
	public boolean removeRecordsAndEnsureIndex() {
		return relatedPhoneDao.removeRecordsAndEnsureIndex();
	}
}
