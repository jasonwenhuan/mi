package com.chinaunicom.filterman.core.bl;

import java.util.Date;
import java.util.List;

import com.chinaunicom.filterman.core.db.entity.RelatedPhoneEntity;

public interface IRelatedPhoneBL {
    public boolean createRelatedPhone(RelatedPhoneEntity relatedPhoneEntity);
    public int getTotalFrequent(String shortPhone, Date startTime, Date EndTime);
	public List<RelatedPhoneEntity> getFullPhonesByShortPhone(String shortPhone);
	public boolean removeRecordsAndEnsureIndex();
}
